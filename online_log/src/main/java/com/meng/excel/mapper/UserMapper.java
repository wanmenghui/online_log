package com.meng.excel.mapper;

import com.meng.excel.dao.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author menghui.wan
 */
@Mapper
public interface UserMapper {

    Integer test();

    Integer insert(@Param("user") User user);

    Integer delete(@Param("id") Integer id);

    Integer update(@Param("user") User user);

    User querySingle(@Param("id") Integer id);

    List<User> queryMuch(@Param("ids") List<Integer> ids);

}
