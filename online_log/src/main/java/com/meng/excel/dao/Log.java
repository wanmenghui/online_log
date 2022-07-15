package com.meng.excel.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author menghui.wan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    private Integer id;
    private String userId;
    private String stack;
    private String sqlContent;
    private Integer resultNumber;
    private Boolean isUsed;
    private LocalDateTime executeTime;
    private Integer logType;
    private String originException;
    private String timeCount;

}
