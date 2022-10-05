package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
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

    private WechatService wechatService;

    @GetMapping("/getSwiperItems")
    public BaseResponse getSwiperItems() {
        List<String> res = wechatService.getSwiperItems();
        return ResponseUtil.success().addData(res);
    }

}
