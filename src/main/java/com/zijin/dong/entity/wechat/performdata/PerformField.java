package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单天的性能数据")
public class PerformField {

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    private String refdate;

    /**
     * 性能数据值
     */
    @ApiModelProperty("性能数据值")
    private String value;
}
