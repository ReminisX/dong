package com.zijin.dong.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.UserInfoVo;
import com.zijin.dong.entity.vo.UserLoginVo;
import com.zijin.dong.service.Impl.StpInterfaceServiceImpl;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@Api("通用无拦截接口")
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
    @ApiOperation(value = "普通用户登录接口", httpMethod = "POST")
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
    @ApiOperation(value = "用户信息获取")
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

    @ApiOperation(value = "普通用户退出", httpMethod = "POST")
    @PostMapping("/logout")
    public BaseResponse exit(){
        boolean b = usersService.exit();
        return b ? ResponseUtil.success().addData("退出成功") : ResponseUtil.success().addData("当前无登录账号");
    }

    @PostMapping("/fileUpload")
    public BaseResponse uploadHead(MultipartFile file){
        
        String imgUrl = usersService.uploadHead(file);
        if (Objects.isNull(imgUrl)){
            return ResponseUtil.faliure();
        }else{
            return ResponseUtil.success().addParam("imgUrl", imgUrl);
        }
    }

}
