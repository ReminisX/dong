package com.zijin.dong.entity.wechat.visitdistribution;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Distribution子集")
public class DistributionSub {

    /**
     * 场景 id，定义在各个 index 下不同
     */
    @ApiModelProperty("场景 id，定义在各个 index 下不同")
    private Integer key;

    /**
     * 该场景 id 访问 pv
     */
    @ApiModelProperty("该场景 id 访问 pv")
    private Integer value;

    /**
     * 该场景 id 访问 uv
     */
    @ApiModelProperty("该场景 id 访问 uv")
    private Integer access_source_visit_uv;

}
