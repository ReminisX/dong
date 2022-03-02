package com.zijin.dong;

import cn.hutool.core.io.FileUtil;
import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
class DongApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    @Test
    void contextLoads() {
        Jedis jedis = jedisPool.getResource();
    }

    @Value("${component.fileSavePath}")
    private String fileSavePath;

    @Test
    void test(){
        System.out.println(fileSavePath);

    }

}
