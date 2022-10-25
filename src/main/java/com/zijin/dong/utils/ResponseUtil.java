package com.zijin.dong.utils;


import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.exception.ServiceExceptionEnum;
import org.springframework.stereotype.Component;

/**
 * @Author ZhangXD
 * @Date 2021/11/10 14:26
 * @Description
 */
@Component
public class ResponseUtil {

    public static BaseResponse state(ServiceExceptionEnum serviceExceptionEnum) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(serviceExceptionEnum.getCode());
        baseResponse.setMessage(serviceExceptionEnum.getMessage());
        return baseResponse;
    }

    public static BaseResponse success(){
        return state(ServiceExceptionEnum.SUCCESS);
    }

    public static BaseResponse faliure(){
        return new BaseResponse().failure();
    }

}
