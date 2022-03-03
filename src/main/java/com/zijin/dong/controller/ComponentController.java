package com.zijin.dong.controller;

import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.base.DtoBase;
import com.zijin.dong.service.ComponentService;
import com.zijin.dong.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/component")
@Api("组件功能")
public class ComponentController {

    private final ComponentService componentServiceImpl;

    @Autowired
    public ComponentController(ComponentService componentServiceImpl) {
        this.componentServiceImpl = componentServiceImpl;
    }

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传接口", httpMethod = "POST")
    public BaseResponse uploadFile(MultipartFile multipartFile){
        DtoBase dto = componentServiceImpl.saveFile(multipartFile);
        if (Objects.isNull(dto) || dto.isEmpty() || !dto.get("code").equals("200")){
            return ResponseUtil.faliure()
                    .addParam("", dto.get("msg"));
        }else{
            return ResponseUtil.success()
                    .addParam("path", dto.get("path"))
                    .addParam("fileName", dto.get("fileName"));
        }
    }

    @PostMapping("/del")
    public BaseResponse delFile(@RequestBody String fileName){
        boolean res = componentServiceImpl.delFile(fileName);
        if (res){
            return ResponseUtil.success();
        }else{
            return ResponseUtil.faliure();
        }
    }

}
