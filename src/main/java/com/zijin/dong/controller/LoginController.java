package com.zijin.dong.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zijin.dong.service.Impl.StpInterfaceServiceImpl;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.UserLoginVo;
import com.zijin.dong.entity.vo.UserRegisterVo;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/login")
@Api("普通用户登录相关接口")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UsersService usersService;

    private final StpInterfaceServiceImpl stpInterfaceServiceImpl;

    @Autowired
    public LoginController(UsersService usersService, StpInterfaceServiceImpl stpInterfaceServiceImpl){
        this.usersService = usersService;
        this.stpInterfaceServiceImpl = stpInterfaceServiceImpl;
    }

    @ApiOperation(value = "普通用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginVo userLoginVo){
        Users users = usersService.login(userLoginVo);
        if (!Objects.isNull(users)){
            List<String> permissonList = stpInterfaceServiceImpl.getPermissionList("12", "2");
            return ResponseUtil.success();
        }else{
            return ResponseUtil.faliure();
        }
    }

    @ApiOperation(value = "普通用户注册", httpMethod = "POST")
    @PostMapping("/register")
    public BaseResponse register(@RequestBody UserRegisterVo userRegisterVo){
        Users users = new Users();
        BeanUtils.copyProperties(userRegisterVo, users);
        users.setIdentifer("normal");
        boolean b = usersService.addUser(users);
        return b ? ResponseUtil.success() : ResponseUtil.faliure();
    }

    @ApiOperation(value = "普通用户查询自己ID", httpMethod = "POST")
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

    @ApiOperation(value = "普通用户退出", httpMethod = "POST")
    @PostMapping("/exit")
    public BaseResponse exit(){
        boolean b = usersService.exit();
        return b ? ResponseUtil.success().addData("退出成功") : ResponseUtil.faliure().addData("当前无登录账号");
    }

}
