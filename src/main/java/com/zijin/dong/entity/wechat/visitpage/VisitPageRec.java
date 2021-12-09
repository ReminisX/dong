package com.zijin.dong.entity.wechat.visitpage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("页面访问数据")
public class VisitPageRec {

    /**
     * 页面路径
     */
    @ApiModelProperty("页面路径")
    private String page_path;

    /**
     * 访问次数
     */
    @ApiModelProperty("访问次数")
    private Integer page_visit_pv;

    /**
     * 访问人数
     */
    @ApiModelProperty("访问人数")
    private Integer page_visit_uv;

    /**
     * 次均停留时长
     */
    @ApiModelProperty("次均停留时长")
    private Double page_staytime_pv;

    /**
     * 进入页次数
     */
    @ApiModelProperty("进入页次数")
    private Integer entrypage_pv;

    /**
     * 退出页次数
     */
    @ApiModelProperty("退出页次数")
    private Integer exitpage_pv;

    /**
     * 页面转发次数
     */
    @ApiModelProperty("页面转发次数")
    private Integer page_share_pv;

    /**
     * 页面转发人数
     */
    @ApiModelProperty("页面转发人数")
    private Integer page_share_uv;

}
