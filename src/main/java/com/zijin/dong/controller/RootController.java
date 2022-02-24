package com.zijin.dong.controller;

import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.service.RootService;
import com.zijin.dong.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/root")
public class RootController {

    private final RootService rootServiceImpl;

    @Autowired
    public RootController(RootService rootServiceImpl) {
        this.rootServiceImpl = rootServiceImpl;
    }

    @PostMapping("/getAllUser")
    public BaseResponse getAllUser(Paging paging){
        Pages<Users> res = rootServiceImpl.getAllUser(paging);
        if (res.getTotalCount() <= 0){
            return ResponseUtil.faliure().setMessage("查询列表为空");
        }else{
            return ResponseUtil.success().addData(res);
        }
    }

}