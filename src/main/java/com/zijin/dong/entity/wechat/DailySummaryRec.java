package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel("用户访问小程序数据概况返回数据")
public class DailySummaryRec {

    /**
     * 列表接收
     */
    @ApiModelProperty("列表接收")
    List<DaliySummaryRecSub> list;

}
