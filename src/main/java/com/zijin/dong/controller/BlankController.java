package com.zijin.dong.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.UserLoginVo;
import com.zijin.dong.service.Impl.StpInterfaceServiceImpl;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public BaseResponse login(@RequestBody UserLoginVo userLoginVo){
        Long id = usersService.login(userLoginVo);
        if (!Objects.isNull(id)){
            SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
            String tokenName = saTokenInfo.getTokenName();
            String tokenValue = saTokenInfo.getTokenValue();
            List<String> powerList = stpInterfaceServiceImpl.getPermissionList(id, null);
            return ResponseUtil.success().addParam("tokenName", tokenName).addParam("tokenValue", tokenValue).addParam("power", powerList);
        }else{
            return ResponseUtil.faliure();
        }
    }

}
