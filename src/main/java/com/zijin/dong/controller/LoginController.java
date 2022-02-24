package com.zijin.dong.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UsersService usersService;

    @Autowired
    public LoginController(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody Users users){
        if (usersService.login(users)){
            return ResponseUtil.success();
        }else{
            return ResponseUtil.faliure();
        }
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody Users users){
        users.setIdentifer("normal");
        boolean b = usersService.addUser(users);
        return b ? ResponseUtil.success() : ResponseUtil.faliure();
    }

    @PostMapping("/getId")
    public BaseResponse getLoginId(){
        System.out.println(StpUtil.getSession().get("user"));
        Long id = null;
        String errMsg = "";
        try{
            id = StpUtil.getLoginIdAsLong();
        }catch (Exception e){
            errMsg = e.getLocalizedMessage();
            logger.error(errMsg);
        }
        return !ObjectUtil.isEmpty(id) ? ResponseUtil.success().addData(id) : ResponseUtil.faliure().addData(errMsg);
    }

    @PostMapping("/exit")
    public BaseResponse exit(){
        boolean b = usersService.exit();
        return b ? ResponseUtil.success().addData("退出成功") : ResponseUtil.faliure().addData("当前无登录账号");
    }

}
