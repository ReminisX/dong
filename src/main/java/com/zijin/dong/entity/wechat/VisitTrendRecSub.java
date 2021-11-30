package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户访问趋势结果子集")
public class VisitTrendRecSub {

    /**
     * 时间
     * 格式为：yyyymmdd-yyyymmdd
     */
    @ApiModelProperty("时间段")
    private String ref_date;

    /**
     * 打开次数（自然周内汇总）
     */
    @ApiModelProperty("打开次数")
    private Integer session_cnt;

    /**
     * 访问次数（自然周内汇总）
     */
    @ApiModelProperty("访问次数")
    private Integer visit_pv;

    /**
     * 访问人数（自然周内去重）
     */
    @ApiModelProperty("访问人数")
    private Integer visit_uv;

    /**
     * 新用户数
     */
    @ApiModelProperty("新用户数")
    private Integer visit_uv_new;

    /**
     * 人均停留时长
     */
    @ApiModelProperty("人均停留时长")
    private Double stay_time_uv;

    /**
     * 次均停留时长
     */
    @ApiModelProperty("次均停留时长")
    private Double stay_time_session;

    /**
     * 平均访问深度
     */
    @ApiModelProperty("平均访问深度")
    private Double visit_depth;

}
