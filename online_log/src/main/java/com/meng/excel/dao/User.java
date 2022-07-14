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
public class User {

    private Integer id;
    private String userId;
    private String userName;
    private String userAccount;
    private String password;
    private String mobile;
    private String email;
    private String sex;
    private boolean isDelete;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
