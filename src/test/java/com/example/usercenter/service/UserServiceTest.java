package com.example.usercenter.service;
import java.util.Date;

import com.example.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setId(0);
        user.setUsername("fenvy");
        user.setUserAccount("zhanghao123");
        user.setAvatarUrl("https://636f-codenav-8grj8px727565176-1256524210.tcb.qcloud.la/img/1621332004478-index.png");
        user.setGender((byte)0);
        user.setUserPassword("123456");
        user.setPhone("1311111111");
        user.setEmail("xxx@qq.com");
        user.setUserStatus(0);


        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result); //断言
    }

    @Test
    void userRegister() {//写单元测试
        String userAccount = "Fenvy";
        String userPassword = "";
        String checkPassword = "123456789";
        long result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userPassword = "123456789";

        userAccount = "yu";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "Fenvy";

        userPassword = "123456";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userPassword = "123456789";

        userAccount = "zhanghao123";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount = "~ ff";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount = "FenvyYe";

        userPassword = "123456789";
        checkPassword = "1234567890";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertTrue(result > 0);
    }
}