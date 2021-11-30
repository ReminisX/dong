package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("访问趋势接受数据集")
public class VisitTrendRec {

    /**
     * 列表接收
     */
    @ApiModelProperty("列表接收")
    List<DaliySummaryRecSub> list;

}
