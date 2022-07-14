package com.meng.excel.service;

import com.meng.excel.dao.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author menghui.wan
 */
public interface IUserService {

    Integer test();

    Integer insert(@Param("user") User user);

    Integer delete(@Param("id") Integer id);

    Integer update(@Param("user") User user);

    User querySingle(@Param("id") Integer id);

    List<User> queryMuch(@Param("ids") List<Integer> ids);

}
