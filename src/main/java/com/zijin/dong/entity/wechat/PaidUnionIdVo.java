package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PaidUnionIdVo {

    /**
     * 接口调用凭证
     */
    @ApiModelProperty("接口调用凭证")
    private String access_token;

    /**
     * 支付用户唯一标识
     */
    @ApiModelProperty("支付用户唯一标识")
    private String openid;

    /**
     * 微信支付订单号
     */
    @ApiModelProperty("微信支付订单号")
    private String transaction_id;

    /**
     * 微信支付分配的商户号，和商户订单号配合使用
     */
    @ApiModelProperty("微信支付分配的商户号，和商户订单号配合使用")
    private String mch_id;

    /**
     * 微信支付商户订单号，和商户号配合使用
     */
    @ApiModelProperty("微信支付商户订单号，和商户号配合使用")
    private String out_trade_no;
}
