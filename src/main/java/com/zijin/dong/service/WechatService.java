package com.zijin.dong.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface WechatService {

    /**
     * 获取主页横幅信息
     * @return 列表形式的url链接
     */
    List<? extends Object> getSwiperItems();

    /**
     * 增加主页滚动横幅图片
     * @return 是否增加成功
     */
    boolean putSwiperItem(String fileName, MultipartFile file);

    /**
     * 删除主页滚动横幅图片
     * @return 是否删除成功
     */
    boolean delSwiperItem(String objectName);

    boolean changeSwiperItem(String name, MultipartFile multipartFile);

    boolean renameSwiperItem(String name, String newName);
}
