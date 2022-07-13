package com.example.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 * @author Fenvy
 */
//序列化接口的实现
@Data //lombok的注解，提供get和set方法而不需要自己写
public class UserLoginRequest implements Serializable {

    //防止序列化冲突的UID
    private static final long serialVersionUID = -543286974259314538L;

    private String userAccount;

    private String userPassword;


}
