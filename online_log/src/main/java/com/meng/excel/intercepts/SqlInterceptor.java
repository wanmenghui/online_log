package com.meng.excel.intercepts;

import com.meng.excel.constant.LogConst;
import com.meng.excel.dao.Log;
import com.meng.excel.mapper.LogMapper;
import com.meng.excel.service.ILogService;
import com.meng.excel.service.LogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * sql信息处理
 *
 * @author menghui.wan
 */

@Intercepts({
    @Signature(
        type= Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
    ),
    @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class}
    ),
    @Signature(
        type = ResultSetHandler.class,
        method = "handleResultSets",
        args={Statement.class}
    )
})
@Component
@Slf4j
public class SqlInterceptor implements Interceptor {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    @Lazy
    ILogService logService;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        for (Object arg : args) {
            if (arg instanceof MappedStatement) {
                if (((MappedStatement)arg).getId().contains("LogMapper")) {
                    return invocation.proceed();
                }
            }
        }
        // 分布式的话可以用redis存标记
        HttpSession session = httpServletRequest.getSession();
        Boolean logFlag = (Boolean)session.getAttribute(LogConst.SESSION_LOG_FLAG);
        if (logFlag == null || !logFlag) {
            return invocation.proceed();
        }
        String sql = "";
        Integer num = 0;
        try {
            // 获取xml中的一个select/update/insert/delete节点，是一条SQL语句
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            // 获取到节点的id,即sql语句的id
            String id = mappedStatement.getId();
            // BoundSql就是封装myBatis最终产生的sql类
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            // 获取节点的配置
            Configuration configuration = mappedStatement.getConfiguration();
            // 获取到最终的sql语句
            sql = showSql(configuration, boundSql);

            System.out.println(id);
            System.out.println(sql);

            Object result = invocation.proceed();
            if (result instanceof ArrayList) {
                ArrayList resultList = (ArrayList) result;
                num = resultList.size();
                System.out.println("结果集数量：" + resultList.size());
            }else if (result instanceof Integer) {
                num = (Integer)result;
                System.out.println("受影响条数：" + result);
            }

        } catch (Exception e) {
        }
        if (StringUtils.isNotBlank(sql)) {
            // 记录日志信息
            Log log = new Log();
            log.setLogType(1);
            // user_id可以用redis存，然后在这取,或者用security的上下文都行
            log.setUserId("abc111");
            log.setSqlContent(sql);
            log.setResultNumber(num);

            logService.insert(log);
        }

        // 执行完上面的任务后，不改变原有的sql执行过程
        return invocation.proceed();

    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }


    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?",
                    Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                            Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                            Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 未知参数，替换？防止错位
                        sql = sql.replaceFirst("\\?", "unknown");
                    }
                }
            }
        }
        return sql;
    }



    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

}
