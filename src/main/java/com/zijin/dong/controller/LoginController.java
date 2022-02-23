package com.zijin.dong.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UsersService usersService;

    @Autowired
    public LoginController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody Users users){
        logger.info("传入参数:" + users);
        boolean b = usersService.addUser(users);
        logger.info("新建用户结果：" + b);
        return b ? ResponseUtil.success() : ResponseUtil.faliure();
    }

    @PostMapping("/test")
    public BaseResponse test(){
        return ResponseUtil.success();
    }

    @PostMapping("/t")
    public BaseResponse t(){
        return ResponseUtil.success();
    }

}
