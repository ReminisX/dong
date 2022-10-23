package com.zijin.dong.entity.vo;

import com.zijin.dong.entity.base.Paging;
import lombok.Data;

@Data
public class CategoryItemVo extends Paging {

    private String category;

    private String name;

}
