package com.zijin.dong;

import com.zijin.dong.utils.ProcessBarUtil;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

@SpringBootTest
@EnableAsync
@Slf4j
class DongApplicationTests {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private AsyncTest asyncTest;

    @Autowired
    private MinioClient minioClient;

    @Test
    void contextLoads() {
        System.out.println(Objects.isNull(minioClient));
    }

    @Value("${component.fileSavePath}")
    private String fileSavePath;

    @Test
    void test() throws InterruptedException {
        int size = 100;
        for (int i = 0; i < 100; i++) {
            ProcessBarUtil.showBar("上传文件", size, i, 5);
            Thread.sleep(500);
        }
    }

    @Test
    void t(){
        System.out.print("abc");
        System.out.print("\b \b");
    }

}
