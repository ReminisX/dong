package com.zijin.dong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zijin.dong.entity.CategoryItem;
import com.zijin.dong.entity.vo.CategoryItemVo;

import java.util.List;
import java.util.Set;

/**
* @author Reminis
* {@code @description} 针对表【category_item】的数据库操作Service
* {@code @createDate} 2022-10-23 16:48:18
 */
public interface CategoryItemService extends IService<CategoryItem> {

    Set<String> getCategories();

    List<CategoryItem> getCategoryItems();

    List<CategoryItem> getItemsByCategory(CategoryItemVo categoryItemVo);
}
