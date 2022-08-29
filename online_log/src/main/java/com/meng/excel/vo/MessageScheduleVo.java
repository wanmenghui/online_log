package com.meng.excel.vo;

import lombok.Data;


/**
 * @author menghui.wan
 */
@Data
public class MessageScheduleVo {

    private String userName;
    private String messageContent;
    private String cronExpression;

}
