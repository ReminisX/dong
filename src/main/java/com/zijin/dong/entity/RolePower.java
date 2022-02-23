package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName role_power
 */
@TableName(value ="role_power")
@Data
public class RolePower implements Serializable {
    /**
     * 权限角色
     */
    @TableField(value = "role")
    private String role;

    /**
     * 路径集合，逗号隔开
     */
    @TableField(value = "power")
    private String power;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}