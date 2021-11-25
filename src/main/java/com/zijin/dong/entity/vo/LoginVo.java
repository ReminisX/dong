package com.zijin.dong.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("登录发送数据")
public class LoginVo {

    /**
     * 小程序 appId
     */
    @ApiModelProperty(value = "小程序 appId")
    String appId;

    /**
     * 小程序 appSecret
     */
    @ApiModelProperty(value = "小程序 appSecret")
    String secret;

    /**
     * 登录时获取的 code
     */
    @ApiModelProperty(value = "登录时获取的 code")
    String jsCode;

    /**
     * 授权类型，此处只需填写 authorization_code
     */
    @ApiModelProperty(value = "授权类型，此处只需填写 authorization_code")
    String grantType;

}
