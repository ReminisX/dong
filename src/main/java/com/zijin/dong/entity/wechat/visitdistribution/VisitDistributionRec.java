package com.zijin.dong.entity.wechat.visitdistribution;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("小程序访问分布返回数据")
public class VisitDistributionRec {

    /**
     * 日期
     * 格式为：yyyymmdd
     */
    @ApiModelProperty("日期")
    private String ref_date;

    /**
     * 数据列表
     */
    @ApiModelProperty("数据列表")
    private List<Distribution> list;

}
