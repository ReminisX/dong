package com.zijin.dong.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.Users;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Service实现
* @createDate 2022-02-22 16:05:07
*/
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService, UserDetailsService {

    private UsersMapper usersMapper;

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper){
        this.usersMapper = usersMapper;
    }

    /**
     * 验证账号
     * @param username 用户名
     * @return User
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("username", username);
        Users users = usersMapper.selectOne(usersQueryWrapper);
        if(users == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new User(username, users.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
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




