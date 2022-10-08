package com.zijin.dong.service.Impl;

import com.zijin.dong.component.MinioComponent;
import com.zijin.dong.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {

    private final MinioComponent minioComponent;

    @Value("${spring.redis.switch}")
    private boolean redisSwitch;

    private final BoundListOperations<String, String> listOps;

    @Autowired
    public WechatServiceImpl(MinioComponent minioComponent, RedisTemplate<String, String> redisTemplate) {
        this.minioComponent = minioComponent;
        listOps = redisTemplate.boundListOps(bucketName);
    }

    /**
     * 存储首页滚动图片的bucket名称
     */
    private String bucketName = "wechat-home-swiper";

    /**
     * 首页滚动图片最大个数
     */
    private Integer maxKeys = 5;

    // 资源过期时间，单位秒
    @Value("${spring.redis.time}")
    private Integer time;

    /**
     * 获取滚动列表对象
     * @return 列表对象的url链接
     */
    @Override
    public List<String> getSwiperItems() {
        if (redisSwitch) {
            List<String> temp = listOps.range(0, maxKeys);
            if (Objects.isNull(temp) || temp.size() == 0) {
                List<String> list = minioComponent.getAllUrlsInBucket(bucketName, maxKeys, time);
                for (String s : list) {
                    listOps.rightPush(s);
                }
                listOps.expire(time, TimeUnit.SECONDS);
                return list;
            } else {
                return temp;
            }
        } else {
            return minioComponent.getAllUrlsInBucket(bucketName, maxKeys, time);
        }
    }

    @Override
    public boolean putSwiperItem(String fileName, MultipartFile file) {
        boolean b = false;
        try {
            b = minioComponent.upload(bucketName, fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean delSwiperItem(String objectName) {
        return minioComponent.delObject(bucketName, objectName);
    }
}
