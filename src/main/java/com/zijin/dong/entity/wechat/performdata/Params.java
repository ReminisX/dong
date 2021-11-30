package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询条件")
public class Params {

    /**
     * 查询条件
     * networktype	网络类型作为查询条件，value=“-1,3g,4g,wifi”分别表示 全部网络类型，3G，4G，WIFI,不传networktype默认为全部网络类型
     * device_level	机型作为查询条件，此时value=“-1,1,2,3”分别表示 全部机型，高档机，中档机，低档机,不传device_level默认为全部机型
     * device	平台作为查询条件，此时value="-1,1,2"分别表示 全部平台，IOS平台，安卓平台,不传device默认为全部平台
     */
    @ApiModelProperty("查询条件")
    private String field;

    /**
     * 查询条件值
     */
    @ApiModelProperty("查询条件值")
    private String value;

}
