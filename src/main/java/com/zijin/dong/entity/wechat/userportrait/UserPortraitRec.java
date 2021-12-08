package com.zijin.dong.entity.wechat.userportrait;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户画像接收数据实体类")
public class UserPortraitRec {

    /**
     * 时间范围
     */
    @ApiModelProperty("时间范围")
    private String ref_date;

    /**
     * 新用户画像
     */
    @ApiModelProperty("新用户画像")
    private Portrait visit_uv_new;

    /**
     * 活跃用户画像
     */
    @ApiModelProperty("活跃用户画像")
    private Portrait visit_uv;

}
