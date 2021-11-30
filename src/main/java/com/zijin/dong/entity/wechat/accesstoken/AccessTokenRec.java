package com.zijin.dong.entity.wechat.accesstoken;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("获取小程序全局唯一后台接口调用凭据返回数据")
public class AccessTokenRec {

    /**
     * 获取到的凭证
     */
    @ApiModelProperty("获取到的凭证")
    private String access_token;

    /**
     * 凭证有效时间，单位：秒
     */
    @ApiModelProperty("凭证有效时间，单位：秒")
    private Long expire_in;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date lastUpdate;

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
