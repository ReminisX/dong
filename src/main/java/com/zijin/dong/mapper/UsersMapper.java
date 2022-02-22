package com.zijin.dong.mapper;

import com.zijin.dong.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Mapper
* @createDate 2022-02-22 16:05:07
* @Entity com.zijin.dong.entity.Users
*/
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}




