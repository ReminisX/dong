package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.LoginRecVo;
import com.zijin.dong.entity.vo.LoginVo;
import com.zijin.dong.service.Impl.WeChatServerImpl;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechat")
@Api("微信模块")
public class WeChatController {

    private final WeChatServerImpl loginServerImpl;

    @Autowired
    public WeChatController(WeChatServerImpl loginServerImpl){
        this.loginServerImpl = loginServerImpl;
    }

    /**
     * 微信登录凭证校验
     * @param loginVo
     * @return BaseResponse
     */
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginVo loginVo){
        LoginRecVo loginRecVo = loginServerImpl.login(loginVo);
        if (loginRecVo.getErrcode() == 0){
            return ResponseUtil.success().addData(loginRecVo);
        }else{
            return ResponseUtil.faliure().addData(loginRecVo);
        }
    }

}
