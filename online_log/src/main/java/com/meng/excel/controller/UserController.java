package com.meng.excel.controller;

import com.meng.excel.dao.User;
import com.meng.excel.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author menghui.wan
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/read")
    public String read() {
        Integer count = userService.test();
        return "success:  " + count;
    }

    @RequestMapping("/insert")
    public Integer insert() {
        User user = new User();
        user.setUserId("abc555");
        user.setUserName("测试新增");
        user.setUserAccount("test_insert");
        user.setPassword("123456");
        user.setMobile("1234567890");
        user.setEmail("12345@qq.com");
        user.setSex("男");
        Integer result = userService.insert(user);
        return result;
    }

    @RequestMapping("/delete/{id}")
    public Integer delete(@PathVariable("id") Integer id) {
        Integer result = userService.delete(id);
        return result;
    }

    @RequestMapping("/update")
    public Integer update() {
        User user = userService.querySingle(4);
        user.setPassword("2222222");
        Integer result = userService.update(user);
        return result;
    }

    @RequestMapping("/query/{id}")
    public User querySingle(@PathVariable("id") Integer id) {
        User user = userService.querySingle(id);
        return user;
    }

    @RequestMapping("/queryMuch")
    public List<User> queryMuch() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        List<User> users = userService.queryMuch(ids);
        userService.delete(6);
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int index = random.nextInt(4) + 1;
            User user = userService.querySingle(7);
            userService.update(user);
        }
        int a = 1/0;
        return users;
    }


}
