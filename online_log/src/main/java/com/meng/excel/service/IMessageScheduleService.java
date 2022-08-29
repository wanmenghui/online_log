package com.meng.excel.service;

import com.meng.excel.dao.MessageSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author menghui.wan
 */
public interface IMessageScheduleService {

    Integer insert(@Param("messageSchedule") MessageSchedule messageSchedule);

    List<MessageSchedule> query();

    MessageSchedule queryById(Integer id);

    MessageSchedule queryByPushKey(String pushKey);

    void updateUserInfoByPushKey(String pushKey, String cronExpression, String messageContent);


}
