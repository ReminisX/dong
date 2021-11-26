package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录发送数据")
public class LoginVo {

    /**
     * 小程序 appId
     */
    @ApiModelProperty(value = "小程序 appId")
    private String appId;

    /**
     * 小程序 appSecret
     */
    @ApiModelProperty(value = "小程序 appSecret")
    private String secret;

    /**
     * 登录时获取的 code
     */
    @ApiModelProperty(value = "登录时获取的 code")
    private String jsCode;

    /**
     * 授权类型，此处只需填写 authorization_code
     */
    @ApiModelProperty(value = "授权类型，此处只需填写 authorization_code")
    private String grantType;

}
