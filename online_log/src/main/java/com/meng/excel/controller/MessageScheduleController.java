package com.meng.excel.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.meng.excel.dao.MessageSchedule;
import com.meng.excel.service.IMessageScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author menghui.wan
 */
@RestController
public class MessageScheduleController {

    @Autowired
    private IMessageScheduleService messageScheduleService;

    public String addTask() {
        return "success";
    }

    @RequestMapping("/beginAllTask")
    public String beginAllTask() {
        List<MessageSchedule> messageScheduleList = messageScheduleService.query();
        for (MessageSchedule messageSchedule : messageScheduleList) {
            String taskId = messageSchedule.getUserId();
            String cronExpression = messageSchedule.getCronExpression();
            CronUtil.schedule(taskId,cronExpression, new Task() {
                @Override
                public void execute() {
                    Console.log(messageSchedule.getUserName() + "  开始执行～～～～～～");
                }
            });
        }

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        return "success";
    }

    public String updateTask() {
        return "success";
    }

}
