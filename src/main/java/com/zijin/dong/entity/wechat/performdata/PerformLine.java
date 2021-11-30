package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("按时间排列的性能数据")
public class PerformLine {

    /**
     * 单天的性能数据
     */
    @ApiModelProperty("单天的性能数据")
    private PerformField fields;

}
