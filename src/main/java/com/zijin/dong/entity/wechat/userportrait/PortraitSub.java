package com.zijin.dong.entity.wechat.userportrait;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("省份")
public class PortraitSub {

    /**
     * 属性值id
     */
    @ApiModelProperty("属性值")
    private Integer id;

    /**
     * 属性值名称，与id对应
     */
    @ApiModelProperty("属性值名称")
    private String name;

    /**
     * 该场景访问uv
     */
    @ApiModelProperty("该场景访问uv")
    private Integer access_source_visit_uv;

}
