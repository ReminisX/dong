package com.zijin.dong.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.zijin.dong.annotation.LogAnnotation;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.UserInfoVo;
import com.zijin.dong.entity.vo.UserLoginVo;
import com.zijin.dong.service.Impl.StpInterfaceServiceImpl;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class BlankController {

    private final Logger logger = LoggerFactory.getLogger(BlankController.class);

    private final UsersService usersService;

    private final StpInterfaceServiceImpl stpInterfaceServiceImpl;

    @Autowired
    public BlankController(UsersService usersService, StpInterfaceServiceImpl stpInterfaceServiceImpl){
        this.usersService = usersService;
        this.stpInterfaceServiceImpl = stpInterfaceServiceImpl;
    }

    @PostMapping("/login")
    @LogAnnotation(value = "登录")
    public ResponseEntity<BaseResponse> login(@RequestBody UserLoginVo userLoginVo){
        Long id = usersService.login(userLoginVo);
        if (!Objects.isNull(id)){
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            String tokenName = saTokenInfo.getTokenName();
            String tokenValue = saTokenInfo.getTokenValue();
            List<String> roleList = stpInterfaceServiceImpl.getRoleList(null, tokenValue);
            return ResponseEntity.ok(
                    ResponseUtil.success()
                            .addParam("name", userLoginVo.getUsername())
                            .addParam("tokenName", tokenName)
                            .addParam("token", tokenValue)
                            .addParam("roles", roleList)
            );
        }else{
            return ResponseEntity.badRequest().body(
                    ResponseUtil.faliure()
            );
        }
    }

    @PostMapping("/getUserInfo")
    @LogAnnotation(value = "获取用户信息")
    public BaseResponse getUserInfo(@RequestParam String token){
        List<String> roleList = stpInterfaceServiceImpl.getRoleList(null, token);
        UserInfoVo userInfoVo = usersService.getUserInfo(token);
        if (Objects.isNull(roleList)){
            return ResponseUtil.success().addParam("roles", "");
        }
        return ResponseUtil.success()
                .addParam("roles", roleList)
                .addParam("name", userInfoVo.getUsername())
                .addParam("avatar", userInfoVo.getHeadPortrait());
    }

    @PostMapping("/logout")
    @LogAnnotation(value = "退出")
    public BaseResponse exit(){
        boolean b = usersService.exit();
        return b ? ResponseUtil.success().addData("退出成功") : ResponseUtil.success().addData("当前无登录账号");
    }

}
