package com.zijin.dong.entity.wechat.visitdistribution;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("返回主体数据")
public class Distribution {

    /**
     * 分布类型
     */
    @ApiModelProperty("分布类型")
    private String index;

    /**
     * 分布数据列表
     */
    @ApiModelProperty("分布数据列表")
    private List<DistributionSub> item_list;

}
