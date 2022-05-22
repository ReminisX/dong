package com.zijin.dong.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.Area;
import com.zijin.dong.service.AreaService;
import com.zijin.dong.mapper.AreaMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author ZhangXD
* @description 针对表【area】的数据库操作Service实现
* @createDate 2022-05-17 15:07:57
*/
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area>
    implements AreaService{

    private final AreaMapper areaMapper;

    public AreaServiceImpl(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    @Override
    public List<String> getAllRegion() {
        List<Area> areas = areaMapper.selectList(null);
        List<String> res = new ArrayList<>();
        for (Area area : areas) {
            res.add(area.getAreaName());
        }
        return res;
    }
}




