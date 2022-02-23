package com.zijin.dong.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.Users;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Service实现
* @createDate 2022-02-22 16:05:07
*/
@Service("userDetailsService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

    private UsersMapper usersMapper;

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper){
        this.usersMapper = usersMapper;
    }

    /**
     * 注册新用户
     * @param users 用户实体
     * @return 是否成功
     */
    @Override
    public boolean addUser(Users users){
        long id = IdUtil.getSnowflakeNextId();
        users.setId(id);
        int res = usersMapper.insert(users);
        return res != 0;
    }
}




