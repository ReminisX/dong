package com.zijin.dong.service;

import com.zijin.dong.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;

import java.util.List;

/**
* @author ZhangXD
* @description 针对表【goods】的数据库操作Service
* @createDate 2022-03-05 14:24:34
*/
public interface GoodsService extends IService<Goods> {

    Pages<Goods> queryAllGoods(Paging paging);

    boolean deleteGoods(Integer id);

    boolean addGoods(Goods goods);

}
