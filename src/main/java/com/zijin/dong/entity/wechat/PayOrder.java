package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微信支付订单相关")
public class PayOrder {

    /**
     * token值
     */
    @ApiModelProperty("token值")
    private String access_token;

    /**
     * 用户openid
     */
    @ApiModelProperty("用户openId")
    private String openid;

    /**
     * 微信支付订单号
     */
    @ApiModelProperty("微信支付订单号")
    private String transaction_id;

    /**
     * 微信支付商户订单号
     */
    @ApiModelProperty("微信支付商户订单号")
    private String out_trade_no;

    /**
     * 微信支付商户号
     */
    @ApiModelProperty("微信支付商户号")
    private String mch_id;



}
