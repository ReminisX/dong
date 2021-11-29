package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("插件唯一标识返回数据")
public class PluginOpenPidRec {

    /**
     * 插件唯一标识码
     */
    @ApiModelProperty("插件唯一标识码")
    private String openpid;

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private Integer errcode;

    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String errmsg;

}
