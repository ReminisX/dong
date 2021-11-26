package com.zijin.dong.entity.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class EncryptedDataRec {

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

    /**
     * 是否是合法数据
     */
    @ApiModelProperty("是否是合法数据")
    private Boolean vaild;

    /**
     * 加密数据生成的时间戳
     */
    @ApiModelProperty("加密数据生成的时间戳")
    private Date create_time;

}
