package com.zijin.dong.entity.base;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.HashMap;

/**
 * @Author ZhangXD
 * @Date 2021/10/24 11:11
 * @Description
 */
public class BaseResponse extends HashMap<String, Object> {

    private String CODE = "code";
    private String STATUS = "status";
    private String MESSAGE = "message";
    private String DATA = "data";

    public BaseResponse() {
        put(CODE, 200);
        put(STATUS, "unknow");
        put(MESSAGE, "未知");
    }

    public BaseResponse(Integer code, String message) {
        put(CODE, code);
        put(STATUS, "Success");
        put(MESSAGE, message);
    }

    public BaseResponse(String code, String message) {
        put(CODE, code);
        put(STATUS, "Failure");
        put(MESSAGE, message);
    }

    /**
     * 验证是否有重复元素
     */
    public void verifyElement(){
        if (containsKey(CODE)){
            remove(CODE);
        }
        if (containsKey(MESSAGE)){
            remove(MESSAGE);
        }
        if (containsKey(STATUS)){
            remove(STATUS);
        }
    }

    public void setCode(String code) {
        if (containsKey(CODE)){
            remove(CODE);
        }
        put(CODE, code);
    }

    public BaseResponse setMessage(String message) {
        if (containsKey(MESSAGE)){
            remove(MESSAGE);
        }
        put(MESSAGE, message);
        return this;
    }

    /**
     * 若成功访问，则返回正确信息
     * @return
     */
    public BaseResponse success() {
        verifyElement();
        put(STATUS, "success");
        put(CODE, 200);
        put(MESSAGE, "操作成功");
        return this;
    }

    /**
     * 若访问失败，则返回错误信息
     * @return
     */
    public BaseResponse failure() {
        verifyElement();
        put(STATUS, "failure");
        put(CODE, 400);
        put(MESSAGE, "操作失败");
        return this;
    }

    /**
     * 添加Data
     * @param obj
     * @return
     */
    public BaseResponse addData(Object obj) {
        if (containsKey(DATA)){
            remove(DATA);
        }
        put(DATA, obj);
        return this;
    }

    /**
     * 添加任意元素
     * @param key
     * @param obj
     * @return
     */
    public BaseResponse addParam(String key, Object obj) {
        if (containsKey(key)){
            remove(key);
        }
        put(key, obj);
        return this;
    }

}
