package com.meng.excel.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author menghui.wan
 */
@Data
@Builder
public class MessageSchedule {

    private Integer id;
    private String userId;
    private String userName;
    private String pushKey;
    private String messageContent;
    private String cronExpression;
    private LocalDateTime expirationTime;
    private Boolean isDelete;
    private LocalDateTime createTime;

}
