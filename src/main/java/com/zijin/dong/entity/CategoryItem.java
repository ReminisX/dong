package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName category_item
 */
@TableName(value ="category_item")
@Data
public class CategoryItem implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 商品唯一id
     */
    private Integer itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 商品类别
     */
    private String itemCategory;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}