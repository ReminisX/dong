package com.zijin.dong.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.CategoryItem;
import com.zijin.dong.entity.vo.CategoryItemVo;
import com.zijin.dong.mapper.CategoryItemMapper;
import com.zijin.dong.service.CategoryItemService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author Reminis
* @description 针对表【category_item】的数据库操作Service实现
* @createDate 2022-10-23 16:48:18
*/
@Service
public class CategoryItemServiceImpl extends ServiceImpl<CategoryItemMapper, CategoryItem>
    implements CategoryItemService{

    private final CategoryItemMapper categoryItemMapper;

    public CategoryItemServiceImpl(CategoryItemMapper categoryItemMapper) {
        this.categoryItemMapper = categoryItemMapper;
    }

    @Override
    public Set<String> getCategories() {
        List<CategoryItem> res = categoryItemMapper.selectList(null);
        Set<String> set = new HashSet<>();
        for (CategoryItem item : res) {
            set.add(item.getItemCategory());
        }
        return set;
    }

    @Override
    public List<CategoryItem> getCategoryItems() {
        return categoryItemMapper.selectList(null);
    }

    @Override
    public List<CategoryItem> getItemsByCategory(CategoryItemVo categoryItemVo) {
        QueryWrapper<CategoryItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_category", categoryItemVo.getCategory());
        return categoryItemMapper.selectList(queryWrapper);
    }
}




