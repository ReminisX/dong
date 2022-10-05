package com.zijin.dong.service.Impl;

import com.zijin.dong.component.MinioComponent;
import com.zijin.dong.service.WechatService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class WechatServiceImpl implements WechatService {

    private MinioComponent minioComponent;

    public WechatServiceImpl(MinioComponent minioComponent) {
        this.minioComponent = minioComponent;
    }

    /**
     * 存储首页滚动图片的bucket名称
     */
    private String bucketName = "wechat-home-swiper";

    /**
     * 首页滚动图片最大个数
     */
    private Integer maxKeys = 5;

    @Override
    public List<String> getSwiperItems() {
        return minioComponent.getAllUrlsInBucket(bucketName, 5);
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
