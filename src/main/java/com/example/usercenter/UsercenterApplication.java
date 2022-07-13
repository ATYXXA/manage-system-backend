package com.example.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//sprintBoot的全局启动入口
@MapperScan("com.example.usercenter.mapper") //写了个注解，从而会扫描mapper包中的文件，将里面的增删改查注入到项目当中
public class UsercenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsercenterApplication.class, args);
    }

}
