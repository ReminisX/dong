package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.base.ImageEntity;
import com.zijin.dong.service.WechatService;
import com.zijin.dong.utils.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class WechatHomeController {

    public WechatHomeController(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    private final WechatService wechatService;

    @GetMapping("/getSwiperItems")
    public BaseResponse getSwiperItems() {
        List<ImageEntity> res = (List<ImageEntity>) wechatService.getSwiperItems();
        return ResponseUtil.success().addData(res);
    }

}
