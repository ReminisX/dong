package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户支付凭证接口返回数据")
public class PaidUnionRec {

    /**
     * 用户唯一标识，调用成功后返回
     */
    @ApiModelProperty("用户唯一标识，调用成功后返回")
    String unionid;

    /**
     *错误码
     */
    @ApiModelProperty("错误码")
    Integer errcode;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    String errmsg;

}
