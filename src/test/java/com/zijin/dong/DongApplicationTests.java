package com.zijin.dong;

import cn.dev33.satoken.stp.StpUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        StpUtil.login(123);
        System.out.println(StpUtil.getTokenName());

    }

}
