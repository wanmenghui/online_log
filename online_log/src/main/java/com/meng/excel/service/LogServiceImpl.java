package com.meng.excel.service;

import com.meng.excel.dao.Log;
import com.meng.excel.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author menghui.wan
 */
@Service
public class LogServiceImpl implements ILogService{

    @Autowired
    private LogMapper logMapper;

    @Override
    public Integer insert(Log log) {
        return logMapper.insert(log);
    }

    @Override
    public List<Log> query(String beginTime, String endTime, String userId) {
        return logMapper.query(beginTime, endTime, userId);
    }

    @Override
    public Integer update(String beginTime, String endTime, String userId) {
        return logMapper.update(beginTime, endTime, userId);
    }
}
