package com.meng.excel.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.pattern.CronPattern;
import cn.hutool.cron.task.Task;
import cn.hutool.http.HttpUtil;
import com.meng.excel.constant.MessageConst;
import com.meng.excel.dao.MessageSchedule;
import com.meng.excel.service.IMessageScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.meng.excel.constant.MessageConst.*;

/**
 * @author menghui.wan
 */
@RestController
public class MessageScheduleController {

    @Autowired
    private IMessageScheduleService messageScheduleService;

    @RequestMapping("/add/{id}")
    public String addTask(@PathVariable Integer id) {
        MessageSchedule messageSchedule = messageScheduleService.queryById(id);
        String taskId = messageSchedule.getUserId();
        String cronExpression = messageSchedule.getCronExpression();
        CronUtil.schedule(taskId,cronExpression, new Task() {
            @Override
            public void execute() {
                Console.log(messageSchedule.getUserName() + "  开始执行～～～～～～");
                HashMap<String, Object> params = new HashMap<>();
                params.put(PUSH_KEY,messageSchedule.getPushKey());
                params.put(TEXT,messageSchedule.getMessageContent());
                HttpUtil.post(MESSAGE_URL,params);
                System.out.println("success");
            }
        });
        return "success add";
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
                    HashMap<String, Object> params = new HashMap<>();
                    params.put(PUSH_KEY,messageSchedule.getPushKey());
                    params.put(TEXT,messageSchedule.getMessageContent());
                    HttpUtil.post(MESSAGE_URL,params);
                    System.out.println("success");
                }
            });
        }

        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        return "success begin";
    }

    @RequestMapping("/update/{taskId}")
    public String updateTask(@PathVariable String taskId) {
        CronUtil.updatePattern(taskId, CronPattern.of("*/1 * * * * ?"));
        return "success update";
    }

    @RequestMapping("/remove/{taskId}")
    public String removeTaSK(@PathVariable String taskId) {
        CronUtil.remove(taskId);
        return "success remove";
    }

    @RequestMapping("/removeAll")
    public String removeTaskAll() {
        CronUtil.stop();
        return "success remove";
    }

}
