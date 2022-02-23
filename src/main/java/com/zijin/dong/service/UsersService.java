package com.zijin.dong.service;

import com.zijin.dong.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Service
* @createDate 2022-02-22 16:05:07
*/
public interface UsersService extends IService<Users> {

    public boolean addUser(Users users);

    public boolean login(Users users);

    public boolean exit();

    public boolean controlUser(Long id, String operation);

}
