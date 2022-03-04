package com.zijin.dong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName file
 */
@TableName(value ="file")
@Data
public class MyFile implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件摘要
     */
    @TableField(value = "hex")
    private String hex;

    /**
     * 文件名
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件路径
     */
    @TableField(value = "file_path")
    private String filePath;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}