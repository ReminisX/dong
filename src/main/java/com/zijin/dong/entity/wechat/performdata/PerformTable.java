package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("返回的数据数组")
public class PerformTable {

    /**
     * 性能数据指标ID
     */
    @ApiModelProperty("性能数据指标ID")
    private String id;

    /**
     * 按时间排列的指标数据
     */
    @ApiModelProperty("按时间排列的指标数据")
    private PerformLine lines;

    /**
     * 性能数据指标中文名
     */
    @ApiModelProperty("性能数据指标中文名")
    private String zh;

}
