package com.zijin.dong.entity.wechat.accesstoken;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取小程序全局唯一后台接口调用凭据发送数据")
public class AccessTokenVo {

    /**
     * 填写 client_credential
     */
    @ApiModelProperty("填写 client_credential")
    private String grant_type;

    /**
     * 小程序唯一凭证
     */
    @ApiModelProperty("小程序唯一凭证")
    private String appid;

    /**
     * 小程序唯一凭证密钥
     */
    @ApiModelProperty("小程序唯一凭证密钥")
    private String secret;

}
