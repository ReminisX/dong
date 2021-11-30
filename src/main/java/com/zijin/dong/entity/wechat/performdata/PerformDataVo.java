package com.zijin.dong.entity.wechat.performdata;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("性能上传数据")
public class PerformDataVo {

    /**
     * 开始和结束的日期时间戳
     * 日期跨度不能超过30天
     */
    @ApiModelProperty("开始和结束的日期时间戳")
    private StageTime time;

    /**
     * 查询数据的类型
     * 10016	打开率, params字段可传入网络类型和机型
     * 10017	启动各阶段耗时，params字段可传入网络类型和机型
     * 10021	页面切换耗时，params数组字段可传入机型
     * 10022	内存指标，params数组字段可传入机型
     * 10023	内存异常，params数组字段可传入机型
     */
    @ApiModelProperty("查询数据的类型")
    private String module;

    /**
     * 查询条件，比如机型，网络类型等等
     */
    @ApiModelProperty("查询条件，比如机型，网络类型等等")
    private Params params;
}
