package com.zijin.dong.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("登录返回数据")
public class LoginRecVo {

    /**
     * 用户唯一标识
     */
    @ApiModelProperty("用户唯一标识")
    String openid;

    /**
     * 会话密钥
     */
    @ApiModelProperty("会话密钥")
    String session_key;

    /**
     * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
     */
    @ApiModelProperty("用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。")
    String unionid;

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    Integer errcode;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    String errmsg;

}
