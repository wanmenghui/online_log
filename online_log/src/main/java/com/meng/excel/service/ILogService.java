package com.meng.excel.service;

import com.meng.excel.dao.Log;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author menghui.wan
 */
public interface ILogService {

    Integer insert(Log log);

    List<Log> query(String beginTime, String endTime, String userId);

    Integer update(String beginTime, String endTime, String userId);

}
