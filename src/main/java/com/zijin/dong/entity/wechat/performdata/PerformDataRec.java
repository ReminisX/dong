package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("性能数据")
public class PerformDataRec {

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

    /**
     * 性能数据
     */
    @ApiModelProperty("性能数据")
    private PerformBody body;
}
