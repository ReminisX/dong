package com.zijin.dong.controller;

import com.zijin.dong.annotation.LogAnnotation;
import com.zijin.dong.entity.Goods;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.service.GoodsService;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminGoods")
@Api("管理员商品管理")
public class AdminGoodsController {

    private final GoodsService goodsService;

    public AdminGoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @PostMapping("/all")
    @ApiOperation("查询全部商品")
    @LogAnnotation(value = "查询全部商品")
    public BaseResponse queryAllGoods(@RequestBody Paging paging){
        Pages<Goods> res = goodsService.queryAllGoods(paging);
        if (res.getTotalCount() > 0){
            return ResponseUtil.success().addData(res);
        }else{
            return ResponseUtil.faliure();
        }
    }

    @LogAnnotation(value = "添加商品")
    public BaseResponse addGoods(@RequestBody Goods goods){
        boolean res = goodsService.addGoods(goods);
        if (res){
            return ResponseUtil.success();
        }else{
            return ResponseUtil.faliure();
        }
    }
}
