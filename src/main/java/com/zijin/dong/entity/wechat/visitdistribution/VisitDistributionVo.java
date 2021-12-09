package com.zijin.dong.entity.wechat.visitdistribution;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("小程序访问分布数据上传")
public class VisitDistributionVo {

    /**
     * 开始时间
     * 格式：yyyymmdd
     */
    @ApiModelProperty("开始时间")
    private String begin_date;

    /**
     * 结束时间
     * 开始日期与结束日期相差的天数限定为0/6/29，分别表示查询最近1/7/30天数据，允许设置的最大值为昨日。
     * 格式：yyyymmdd
     */
    @ApiModelProperty("结束时间")
    private String end_date;

}
