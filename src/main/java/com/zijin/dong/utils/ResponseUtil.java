package com.zijin.dong.utils;


import com.zijin.dong.entity.base.BaseResponse;
import org.springframework.stereotype.Component;

/**
 * @Author ZhangXD
 * @Date 2021/11/10 14:26
 * @Description
 */
@Component
public class ResponseUtil {

    public static BaseResponse baseResponse(){
        return new BaseResponse();
    }

    public static BaseResponse success(){
        return new BaseResponse().success();
    }

    public static BaseResponse faliure(){
        return new BaseResponse().failure();
    }

}
