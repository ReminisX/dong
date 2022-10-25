package com.zijin.dong.exception;

public final class ServiceException extends RuntimeException{

    /**
     * 错误码
     */
    private final String code;

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum) {
        // 使用父类的message字段
        super(serviceExceptionEnum.getMessage());
        // 设置自己的错误码
        this.code = serviceExceptionEnum.getCode();
    }

}
