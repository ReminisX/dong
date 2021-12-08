package com.zijin.dong.entity.wechat.userportrait;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("画像实体数据")
public class Portrait {

    /**
     * 分布类型
     */
    @ApiModelProperty("分布类型")
    private Integer index;

    /**
     * 省份
     * 如：北京、广东
     */
    @ApiModelProperty("省份")
    private PortraitSub province;

    /**
     * 城市
     * 如：北京、广州
     */
    @ApiModelProperty("城市")
    private PortraitSub city;

    /**
     * 性别
     * 如：男、女、未知
     */
    @ApiModelProperty("性别")
    private PortraitSub genders;

    /**
     * 平台类型
     * 如：iPhone、Android
     */
    @ApiModelProperty("平台类型")
    private PortraitSub platforms;

    /**
     * 机型
     * 如：苹果iPhone6、OPPO R9
     */
    @ApiModelProperty("机型")
    private PortraitSub devices;

    /**
     * 年龄
     * 包括17岁以下、18-24岁等区间
     */
    private PortraitSub ages;

}
