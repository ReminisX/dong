package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_power
 */
@TableName(value ="user_power")
@Data
public class UserPower implements Serializable {
    /**
     * 用户身份唯一标识
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户权限
     */
    @TableField(value = "power")
    private String power;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}