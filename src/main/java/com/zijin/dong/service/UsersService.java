package com.zijin.dong.service;

import com.zijin.dong.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijin.dong.entity.vo.UserLoginVo;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Service
* @createDate 2022-02-22 16:05:07
*/
public interface UsersService extends IService<Users> {

    boolean addUser(Users users);

    Long login(UserLoginVo userLoginVo);

    boolean exit();

}
