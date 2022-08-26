package com.meng.excel.task;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.UUID;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.meng.excel.dao.MessageSchedule;
import com.meng.excel.service.IMessageScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author menghui.wan
 */
@Component
public class CronUtilTest implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("111");
    }
}
