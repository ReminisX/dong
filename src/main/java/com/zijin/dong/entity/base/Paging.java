package com.zijin.dong.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;

@Data
public class Paging {

    /**
     * 页码
     */
    @TableField(exist = false)
    private Integer page;

    /**
     * 个数
     */
    @TableField(exist = false)
    private Integer num;

}
