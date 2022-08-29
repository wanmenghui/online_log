package com.meng.excel.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meng.excel.dao.Log;
import com.meng.excel.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * 异常拦截处理
 *
 * @author menghui.wan
 */
//@RestControllerAdvice
public class ExceptionHandle {

    @Autowired
    private ILogService logService;

    @ExceptionHandler(Exception.class)
    public JSONObject handlerException(Exception e) {
        Object json = JSON.toJSON(e);
        JSONObject result = JSON.parseObject(json.toString());
        String stack = dealExceptionInfo(e);
        Log log = new Log();
        log.setLogType(0);
        log.setStack(stack);
        log.setOriginException(json.toString());
        // user_id可以用redis存，然后在这取,或者用security的上下文都行
        log.setUserId("abc111");
        logService.insert(log);
        return result;
    }


    private String dealExceptionInfo(Exception e) {
        String className = e.getClass().getName();
        StringBuffer str = new StringBuffer();
        str.append(className).append(": ").append(e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement traceElement : stackTrace) {
            str.append(" at: ")
                .append(traceElement.getClassName()).append(" : ")
                .append(traceElement.getMethodName())
                .append("  ").append(traceElement.getLineNumber()).append("行");
        }
        return str.toString();
    }

}
