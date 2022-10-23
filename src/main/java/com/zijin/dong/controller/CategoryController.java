package com.zijin.dong.controller;

import com.zijin.dong.entity.CategoryItem;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.CategoryItemVo;
import com.zijin.dong.service.CategoryItemService;
import com.zijin.dong.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryItemService categoryItemService;

    public CategoryController(CategoryItemService categoryItemService) {
        this.categoryItemService = categoryItemService;
    }

    @GetMapping("/getCategories")
    public BaseResponse getCategories() {
        Set<String> res = categoryItemService.getCategories();
        return res.size() == 0 ? ResponseUtil.faliure() : ResponseUtil.success().addData(res);
    }

    @GetMapping("/getCategoryItems")
    public BaseResponse getCategoryItems() {
        List<CategoryItem> res = categoryItemService.getCategoryItems();
        return res.size() == 0 ? ResponseUtil.faliure() : ResponseUtil.success().addData(res);
    }

    @PostMapping("/getItemsByCategory")
    public BaseResponse getItemsByCategory(@RequestBody CategoryItemVo categoryItemVo) {
        List<CategoryItem> res = categoryItemService.getItemsByCategory(categoryItemVo);
        return res.size() == 0 ? ResponseUtil.faliure() : ResponseUtil.success().addData(res);
    }

}
