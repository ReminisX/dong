package com.zijin.dong.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.Goods;
import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.service.GoodsService;
import com.zijin.dong.mapper.GoodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author ZhangXD
* @description 针对表【goods】的数据库操作Service实现
* @createDate 2022-03-05 14:24:34
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService{

    private final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    /**
     * 分页查询所有商品
     * @param paging 分页方式
     * @return 当前页面信息
     */
    @Override
    public Pages<Goods> queryAllGoods(Paging paging) {
        IPage<Goods> iPage = new Page<>(paging.getPage(), paging.getNum());
        iPage = goodsMapper.selectPage(iPage, null);
        return new Pages<>(iPage);
    }

    /**
     * 删除商品信息
     * @param id 商品id
     * @return 删除结果
     */
    @Override
    public boolean deleteGoods(Integer id) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gid", id);
        int res = goodsMapper.delete(queryWrapper);
        if (res > 0){
            logger.info("商品[" + id + "]删除成功");
            return true;
        }else{
            logger.error("商品[" + id + "]删除失败");
            return false;
        }
    }

    @Override
    public boolean addGoods(Goods goods) {
        Long id = IdUtil.getSnowflakeNextId();
        goods.setGid(id);
        int res = goodsMapper.insert(goods);
        if (res > 0){
            logger.info("商品[" + goods.getGname() + "]添加成功");
            return true;
        }else{
            logger.error("商品[" + goods.getGname() + "]添加失败");
            return false;
        }
    }
}




