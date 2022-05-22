package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName area
 */
@TableName(value ="area")
@Data
public class Area implements Serializable {
    /**
     * 自增序列
     */
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    /**
     * 地域id
     */
    @TableField(value = "area_id")
    private String areaId;

    /**
     * 地域名称
     */
    @TableField(value = "area_name")
    private String areaName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}