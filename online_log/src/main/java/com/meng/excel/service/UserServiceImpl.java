package com.meng.excel.service;

import com.meng.excel.dao.User;
import com.meng.excel.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author menghui.wan
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer test() {
        return userMapper.test();
    }

    @Override
    public Integer insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public Integer delete(Integer id) {
        return userMapper.delete(id);
    }

    @Override
    public Integer update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User querySingle(Integer id) {
        return userMapper.querySingle(id);
    }

    @Override
    public List<User> queryMuch(List<Integer> ids) {
        return userMapper.queryMuch(ids);
    }
}
