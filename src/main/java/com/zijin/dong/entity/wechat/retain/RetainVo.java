package com.zijin.dong.entity.wechat.retain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取用户访问小程序日留存")
public class RetainVo {

    /**
     * 开始日期
     * 日期格式：yyyymmdd
     */
    @ApiModelProperty("开始日期")
    private String begin_date;

    /**
     * 结束日期
     * 日期格式：yyyymmdd
     */
    @ApiModelProperty("结束日期")
    private String end_date;

}
