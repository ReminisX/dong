package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.wechat.LoginRec;
import com.zijin.dong.entity.wechat.LoginVo;
import com.zijin.dong.service.Impl.WeChatServerImpl;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("微信登录凭证校验")
    public BaseResponse login(@RequestBody LoginVo loginVo){
        LoginRec loginRec = loginServerImpl.login(loginVo);
        if (loginRec.getErrcode() == 0){
            return ResponseUtil.success().addData(loginRec);
        }else{
            return ResponseUtil.faliure().addData(loginRec);
        }
    }

}
