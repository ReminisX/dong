package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("时间子类")
public class StageTime {

    /**
     * 结束日期时间戳
     */
    @ApiModelProperty("结束日期时间戳")
    private String end_timestamp;

    /**
     * 开始日期时间戳
     */
    @ApiModelProperty("开始日期时间戳")
    private String begin_timestamp;

}
