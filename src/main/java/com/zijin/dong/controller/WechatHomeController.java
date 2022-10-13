package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.base.ImageEntity;
import com.zijin.dong.entity.vo.SwiperItemVo;
import com.zijin.dong.service.WechatService;
import com.zijin.dong.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/delSwiperItem")
    public BaseResponse delSwiperItem(@RequestBody SwiperItemVo swiperItemVo) {
        boolean isDel = wechatService.delSwiperItem(swiperItemVo.getName());
        return isDel ? ResponseUtil.success() : ResponseUtil.faliure();
    }

    @PostMapping("/changeSwiperItem")
    public BaseResponse changeSwiperItem(@RequestParam String name, @RequestParam MultipartFile file) {
        boolean isChange = wechatService.changeSwiperItem(name, file);
        return isChange ? ResponseUtil.success() : ResponseUtil.faliure();
    }

    @PostMapping("/renameSwiperItem")
    public BaseResponse renameSwiperItem(@RequestBody SwiperItemVo swiperItemVo) {
        boolean isRename = wechatService.renameSwiperItem(swiperItemVo.getName(), swiperItemVo.getNewName());
        return isRename ? ResponseUtil.success() : ResponseUtil.faliure();
    }

}
