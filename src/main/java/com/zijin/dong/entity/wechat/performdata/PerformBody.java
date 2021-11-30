package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("性能数据")
public class PerformBody {

    /**
     * 返回的数据数组
     */
    @ApiModelProperty("返回的数据数组")
    private PerformTable[] tables;

    /**
     * 数组大小
     */
    @ApiModelProperty("数组大小")
    private Integer count;

}
