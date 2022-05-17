package com.zijin.dong.service;

import com.zijin.dong.entity.Area;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ZhangXD
* @description 针对表【area】的数据库操作Service
* @createDate 2022-05-17 15:07:58
*/
public interface AreaService extends IService<Area> {

    List<String> getAllRegion();
}
