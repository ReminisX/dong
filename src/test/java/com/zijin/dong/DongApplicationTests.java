package com.zijin.dong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zijin.dong.entity.Users;
import com.zijin.dong.utils.ProcessBarUtil;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import redis.clients.jedis.JedisPool;

import java.util.*;

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
    void test() {
        BucketExistsArgs b = new BucketExistsArgs();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void t() throws JsonProcessingException {
        List<Users> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Users person = new Users();
            person.setId(28374914837502L);
            person.setNum(new Random().nextInt(100));
            person.setCreateTime(new Date());
            list.add(person);
        }
        String res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        List<Users> r = objectMapper.readValue(res, new TypeReference<List<Users>>() {
        });
        System.out.println(r);
    }

}
