package com.meng.excel.mapper;

import com.meng.excel.dao.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author menghui.wan
 */
@Mapper
public interface LogMapper {

    Integer insert(@Param("log") Log log);

    List<Log> query(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("userId") String userId);

    Integer update(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("userId") String userId);

}
