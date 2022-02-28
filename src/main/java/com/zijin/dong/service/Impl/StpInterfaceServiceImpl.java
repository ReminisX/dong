package com.zijin.dong.service.Impl;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.entity.RolePower;
import com.zijin.dong.entity.Users;
import com.zijin.dong.mapper.RolePowerMapper;
import com.zijin.dong.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StpInterfaceServiceImpl implements StpInterface {

    private final RolePowerMapper rolePowerMapper;

    private final UsersMapper usersMapper;

    @Autowired
    public StpInterfaceServiceImpl(UsersMapper usersMapper, RolePowerMapper rolePowerMapper){
        this.usersMapper = usersMapper;
        this.rolePowerMapper = rolePowerMapper;
    }

    /**
     * 返回一个账号所拥有的权限码集合
     * @param o 登录ID
     * @param s 登录类别
     * @return 权限集合
     */
    @Override
    public List<String> getPermissionList(Object o, String s) {
        List<String> roles = getRoleList(o, s);
        List<String> list = new ArrayList<>();
        QueryWrapper<RolePower> rolePowerQueryWrapper = new QueryWrapper<>();
        for(String role : roles){
            rolePowerQueryWrapper.eq("role", role);
        }
        List<RolePower> rolePowerList = rolePowerMapper.selectList(rolePowerQueryWrapper);
        for (RolePower r : rolePowerList){
            list.add(r.getPower());
        }
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param o 登录ID
     * @param s 登录类别
     * @return 账号所拥有的角色类别
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id", o);
        Users users = usersMapper.selectOne(usersQueryWrapper);
        return new ArrayList<>(Arrays.asList(users.getIdentifer().split(",")));
    }
}
