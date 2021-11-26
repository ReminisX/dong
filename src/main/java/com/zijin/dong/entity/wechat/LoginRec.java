package com.zijin.dong.entity.wechat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录返回数据")
public class LoginRec {

    /**
     * 用户唯一标识
     */
    @ApiModelProperty("用户唯一标识")
    private String openid;

    /**
     * 会话密钥
     */
    @ApiModelProperty("会话密钥")
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
     */
    @ApiModelProperty("用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。")
    private String unionid;

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private Integer errcode;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String errmsg;

}
