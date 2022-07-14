package com.meng.excel.controller;

import com.meng.excel.constant.LogConst;
import com.meng.excel.dao.Log;
import com.meng.excel.dao.User;
import com.meng.excel.service.ILogService;
import com.meng.excel.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author menghui.wan
 */
@RestController
public class LogController {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    ILogService logService;

    @PostMapping("/begin")
    public String beginRecording() {
        // 开启日志记录
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(LogConst.SESSION_LOG_FLAG,true);
        return "success";
    }


    @GetMapping("/end")
    public Map<Integer, List<Log>> endRecording(@RequestParam("beginTime") String beginTime,
                                                @RequestParam("endTime") String endTime,
                                                @RequestParam("userId") String userId) {
        // 关闭日志记录标志
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(LogConst.SESSION_LOG_FLAG,false);

        List<Log> logList = logService.query(beginTime, endTime, userId);
        Map<Integer, List<Log>> logMapByType = logList.stream().collect(groupingBy(Log::getLogType));

        logService.update(beginTime, endTime, userId);
        return logMapByType;
    }


}
