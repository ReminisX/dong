package com.zijin.dong.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.vo.UserLoginVo;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.mapper.UsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Service实现
* @createDate 2022-02-22 16:05:07
*/
@Service("userDetailsService")
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
    implements UsersService{

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersMapper usersMapper;

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
        users.setCreateTime(new Date());
        int res = usersMapper.insert(users);
        if (res != 0){
            logger.info("用户[" + users.getUsername() + "]添加成功");
            return true;
        }else{
            logger.warn("用户[" + users.getUsername() + "]添加失败");
            return false;
        }
    }

    /**
     * 用户登录
     * @param userLoginVo
     * @return
     */
    @Override
    @Transactional
    public Long login(UserLoginVo userLoginVo) {
        Users users = new Users();
        BeanUtils.copyProperties(userLoginVo, users);
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", users.getUsername()).eq("password", users.getPassword());
        Users user = usersMapper.selectOne(queryWrapper);
        if (!ObjectUtil.isEmpty(user)){
            StpUtil.login(user.getId(), userLoginVo.isRemember());
            user.setToken(StpUtil.getTokenValue());
            user.setLoginTime(new Date());
            usersMapper.updateById(user);
            logger.info("用户[" + users.getUsername() + "]登录成功");
            return user.getId();
        }else{
            logger.warn("用户[" + users.getUsername() + "]登录失败");
            return null;
        }
    }

    @Override
    public boolean exit() {
        Long id = null;
        try{
            id = StpUtil.getLoginIdAsLong();
            Users user = new Users();
            user.setId(id);
            user.setToken("");
            usersMapper.updateById(user);
            logger.info("[" + id + "]下线");
            StpUtil.logout();
        }catch (Exception e){
            logger.warn(e.getLocalizedMessage());
        }
        return id != null;
    }
}




