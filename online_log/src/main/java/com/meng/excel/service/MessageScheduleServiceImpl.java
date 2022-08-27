package com.meng.excel.service;

import com.meng.excel.dao.MessageSchedule;
import com.meng.excel.mapper.MessageScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author menghui.wan
 */
@Service
public class MessageScheduleServiceImpl implements IMessageScheduleService{

    @Autowired
    private MessageScheduleMapper messageScheduleMapper;

    @Override
    public Integer insert(MessageSchedule messageSchedule) {
        return messageScheduleMapper.insert(messageSchedule);
    }

    @Override
    public List<MessageSchedule> query() {
        return messageScheduleMapper.query();
    }

    @Override
    public MessageSchedule queryById(Integer id) {
        return messageScheduleMapper.queryById(id);
    }
}
