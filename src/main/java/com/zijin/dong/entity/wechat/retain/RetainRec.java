package com.zijin.dong.entity.wechat.retain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel("用户访问小程序日留存返回数据")
public class RetainRec {

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    private String ref_date;

    /**
     * 新增用户留存
     */
    @ApiModelProperty("新增用户留存")
    private Map<Integer, Integer> visit_uv_new;

    /**
     * 活跃用户留存
     */
    @ApiModelProperty("活跃用户留存")
    private Map<Integer, Integer> visit_uv;

}
