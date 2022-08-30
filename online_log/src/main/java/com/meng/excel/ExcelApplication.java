package com.meng.excel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ExcelApplication {

    public static void main(String[] args) {
        // 解决docker启动时区不对问题
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(ExcelApplication.class, args);
    }
}
