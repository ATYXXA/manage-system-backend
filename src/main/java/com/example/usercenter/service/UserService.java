package com.example.usercenter.service;

import com.example.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
//接口只定义方法而不写实现，只说明要做什么
public interface UserService extends IService<User> {

    /**
     * 这种注释斜杠加**然后回车即可生成
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户的id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     * @param userAccount 账户名
     * @param userPassword 密码
     * @param request HTTP请求
     * @return 用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 数据库返回的用户信息
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originUser);

    /**
     * 退出登录
     * @param request HTTP请求
     * @return
     */
    int userLogout(HttpServletRequest request);
}
