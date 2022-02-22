package com.zijin.dong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

@SpringBootTest
class DongApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    @Test
    void contextLoads() {
        Jedis jedis = jedisPool.getResource();
    }

    @Test
    void test(){
        //创建解析器
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        //对密码进行加密
        String password = encoder.encode("123");
        System.out.println("------------"+password);

        //判断原字符加密后和内容是否匹配
        boolean result = encoder.matches("123",password);
        System.out.println("============="+result);
    }

}
