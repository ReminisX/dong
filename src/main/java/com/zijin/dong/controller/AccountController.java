package com.zijin.dong.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zijin.dong.annotation.LogAnnotation;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.base.UploadData;
import com.zijin.dong.service.Impl.StpInterfaceServiceImpl;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final UsersService usersService;

    private final StpInterfaceServiceImpl stpInterfaceServiceImpl;

    @Autowired
    public AccountController(UsersService usersService, StpInterfaceServiceImpl stpInterfaceServiceImpl){
        this.usersService = usersService;
        this.stpInterfaceServiceImpl = stpInterfaceServiceImpl;
        RequestMappingHandlerAdapter r;
    }

    @PostMapping("/getId")
    @LogAnnotation(value = "查询自己ID")
    public BaseResponse getLoginId(HttpServletRequest request){
        Long id = null;
        String errMsg = "";
        try{
            id = StpUtil.getLoginIdAsLong();
        }catch (Exception e){
            errMsg = e.getLocalizedMessage();
            logger.error(errMsg);
        }
        return !ObjectUtil.isEmpty(id) ? ResponseUtil.success().addParam("id", id) : ResponseUtil.faliure().addData(errMsg);
    }

    @PostMapping("/fileUpload")
    @LogAnnotation(value = "上传头像")
    public BaseResponse uploadHead(MultipartFile file, UploadData data){
        boolean b = usersService.uploadHead(file, data.getName());
        if (!b){
            return ResponseUtil.faliure();
        }else{
            return ResponseUtil.success();
        }
    }

}
