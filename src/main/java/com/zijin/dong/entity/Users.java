package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zijin.dong.entity.base.Paging;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName users
 */
@TableName(value ="users")
@Data
public class Users extends Paging implements Serializable {
    /**
     * 唯一值
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 身份
     */
    @TableField(value = "power")
    private String power;

    /**
     * 账号创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 账号上次登录时间
     */
    @TableField(value = "login_time")
    private Date loginTime;

    /**
     * 账号最近一次登录获取的token值
     */
    @TableField(value = "token")
    private String token;

    /**
     * 账号封禁开始时间
     */
    @TableField(value = "forbid")
    private Date forbid;

    /**
     * 头像路径
     */
    @TableField(value = "head_img")
    private String headImg;

    /**
     * 账号状态
     */
    @TableField(value = "status")
    private String status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}