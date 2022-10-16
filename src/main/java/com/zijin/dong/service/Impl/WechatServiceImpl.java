package com.zijin.dong.service.Impl;

import com.zijin.dong.component.MinioComponent;
import com.zijin.dong.entity.base.ImageEntity;
import com.zijin.dong.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {

    private final MinioComponent minioComponent;

    @Value("${spring.redis.switch}")
    private boolean redisSwitch;

    private final BoundHashOperations<String, String, String> hashOps;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public WechatServiceImpl(MinioComponent minioComponent, RedisTemplate<String, String> redisTemplate) {
        this.minioComponent = minioComponent;
        this.redisTemplate = redisTemplate;
        hashOps = redisTemplate.boundHashOps(bucketName);
    }

    /**
     * 存储首页滚动图片的bucket名称
     */
    private final String bucketName = "wechat-home-swiper";

    /**
     * 首页滚动图片最大个数
     */
    private final Integer maxKeys = 5;

    // 资源过期时间，单位秒
    @Value("${spring.redis.time}")
    private Integer time;

    /**
     * 获取滚动列表对象
     * @return 列表对象的url链接
     */
    @Override
    public List<ImageEntity> getSwiperItems() {
        if (redisSwitch) {
            List<ImageEntity> res = new ArrayList<>();
            if (!Objects.isNull(hashOps.keys())) {
                for (String key : Objects.requireNonNull(hashOps.keys())) {
                    ImageEntity imageEntity = new ImageEntity();
                    imageEntity.setName(key);
                    imageEntity.setUrl(hashOps.get(key));
                    res.add(imageEntity);
                }
            }
            if (res.size() == 0) {
                List<ImageEntity> list = minioComponent.getAllUrlsInBucket(bucketName, maxKeys, time);
                for (ImageEntity imageEntity : list) {
                    hashOps.put(imageEntity.getName(), imageEntity.getUrl());
                }
                hashOps.expire(time, TimeUnit.SECONDS);
                return list;
            } else {
                return res;
            }
        } else {
            return minioComponent.getAllUrlsInBucket(bucketName, maxKeys, time);
        }
    }

    /**
     * 上传新文件
     * @param fileName 文件名
     * @param file 文件流
     * @return 是否上传成功
     */
    @Override
    public boolean putSwiperItem(String fileName, MultipartFile file) {
        boolean b = false;
        try {
            b = minioComponent.upload(bucketName, fileName, file.getInputStream());
            if (redisSwitch) {
                String url = minioComponent.getObjectUrl(bucketName, fileName, time);
                hashOps.put(fileName, url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 删除对象
     * @param objectName 对象名
     * @return 是否成功
     */
    @Override
    public boolean delSwiperItem(String objectName) {
        if (minioComponent.delObject(bucketName, objectName)) {
            if (redisSwitch) {
                hashOps.delete(objectName);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更改文件源
     * @param name 待更改的文件名称
     * @param multipartFile 新文件源
     * @return 是否更改成功
     */
    @Override
    public boolean changeSwiperItem(String name, MultipartFile multipartFile) {
        if (delSwiperItem(name) && putSwiperItem(name, multipartFile)) {
            if (redisSwitch) {
                String url = minioComponent.getObjectUrl(bucketName, name, time);
                hashOps.put(name, url);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重命名文件
     * @param name 旧文件名
     * @param newName 新文件名
     * @return 是否更名成功
     */
    @Override
    public boolean renameSwiperItem(String name, String newName) {
        if (minioComponent.renameItem(bucketName, name, newName)) {
            if (redisSwitch) {
                hashOps.delete(name);
                String url = minioComponent.getObjectUrl(bucketName, newName, time);
                hashOps.put(newName, url);
            }
            return true;
        } else {
            return false;
        }
    }
}
