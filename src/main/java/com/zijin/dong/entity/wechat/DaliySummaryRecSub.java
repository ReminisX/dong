package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("获取用户访问小程序数据概况-子类")
public class DaliySummaryRecSub {

    /**
     * 日期
     * 格式为：yyyymmdd
     */
    private String ref_date;

    /**
     * 累计用户数
     */
    private Integer visit_total;

    /**
     * 转发次数
     */
    private Integer share_pv;

    /**
     * 转发人数
     */
    private Integer share_uv;
}
