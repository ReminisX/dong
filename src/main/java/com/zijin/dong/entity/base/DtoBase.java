package com.zijin.dong.entity.base;

import java.util.HashMap;

public class DtoBase extends HashMap<String, Object> {

    private final String CODE = "code";
    private final String STATUS = "status";
    private final String MESSAGE = "msg";
    private final String DATA = "data";

    public DtoBase() {
        put(CODE, 200);
        put(STATUS, "unknow");
        put(MESSAGE, "未知");
    }

    public DtoBase(Integer code, String message) {
        put(CODE, code);
        put(STATUS, "Success");
        put(MESSAGE, message);
    }

    public DtoBase(String code, String message) {
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

    public DtoBase setCode(String code) {
        if (containsKey(CODE)){
            remove(CODE);
        }
        put(CODE, code);
        return this;
    }

    public DtoBase setMessage(String message) {
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
    public DtoBase success() {
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
    public DtoBase failure() {
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
    public DtoBase addData(Object obj) {
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
    public DtoBase addParam(String key, Object obj) {
        if (containsKey(key)){
            remove(key);
        }
        put(key, obj);
        return this;
    }

}
