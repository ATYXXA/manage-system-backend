package com.example.usercenter;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@SpringBootTest
//UsercenterApplicationTests默认对应的入口类为UsercenterApplication
class UsercenterApplicationTests {

    @Test
    void testDigest() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //String newPassword = DigestUtils.md5DigestAsHex(("afashdlk"+"mypassword").getBytes());
        //System.out.println("加密后的密码为:"+newPassword);
    }


    @Test
    void contextLoads()  {

    }

}
