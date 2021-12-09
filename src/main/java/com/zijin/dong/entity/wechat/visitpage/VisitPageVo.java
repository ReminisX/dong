package com.zijin.dong.entity.wechat.visitpage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("访问页面上传数据")
public class VisitPageVo {

    /**
     * 开始时间
     * 格式：yyyymmdd
     */
    @ApiModelProperty("开始时间")
    private String begin_date;

    /**
     * 结束时间
     * 限定查询1天数据，允许设置的最大值为昨日
     * 格式：yyyymmdd
     */
    @ApiModelProperty("结束时间")
    private String end_date;

}
