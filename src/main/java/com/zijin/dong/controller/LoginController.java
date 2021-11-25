package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.LoginRecVo;
import com.zijin.dong.entity.vo.LoginVo;
import com.zijin.dong.service.Impl.LoginServerImpl;
import com.zijin.dong.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginServerImpl loginServerImpl;

    @Autowired
    public LoginController(LoginServerImpl loginServerImpl){
        this.loginServerImpl = loginServerImpl;
    }

    @PostMapping("/verify")
    public BaseResponse loginVerify(@RequestBody LoginVo loginVo){
        LoginRecVo loginRecVo = loginServerImpl.loginVerify(loginVo);
        if (loginRecVo.getErrcode() == 0){
            return ResponseUtil.success().addData(loginRecVo);
        }else{
            return ResponseUtil.faliure().addData(loginRecVo);
        }
    }

}
