package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName goods
 */
@TableName(value ="goods")
@Data
public class Goods implements Serializable {
    /**
     * 商品ID
     */
    @TableId(value = "gid")
    private Long gid;

    /**
     * 商品名称
     */
    @TableField(value = "gname")
    private String gname;

    /**
     * 商品价格
     */
    @TableField(value = "gprice")
    private BigDecimal gprice;

    /**
     * 会员价格
     */
    @TableField(value = "vip_price")
    private BigDecimal vip_price;

    /**
     * 商品数量
     */
    @TableField(value = "gnum")
    private Integer gnum;

    /**
     * 商品描述(65535字以下)
     */
    @TableField(value = "describe")
    private String describe;

    /**
     * 商品图片路径
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 商品名称
     */
    @TableField(value = "picture_name")
    private String picture_name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}