package com.zijin.dong;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@SpringBootTest
@EnableAsync
@Slf4j
class DongApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private AsyncTest asyncTest;

    @Test
    void contextLoads() {
        Jedis jedis = jedisPool.getResource();
    }

    @Value("${component.fileSavePath}")
    private String fileSavePath;

    @Test
    void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            asyncTest.add();
        }
    }

}
