package com.zijin.dong.config;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.entity.RolePower;
import com.zijin.dong.entity.UserPower;
import com.zijin.dong.entity.Users;
import com.zijin.dong.mapper.RolePowerMapper;
import com.zijin.dong.mapper.UserPowerMapper;
import com.zijin.dong.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class StpInterfaceConfig implements StpInterface {

    private final RolePowerMapper rolePowerMapper;

    private final UserPowerMapper userPowerMapper;

    private final UsersMapper usersMapper;

    @Autowired
    public StpInterfaceConfig(UsersMapper usersMapper, RolePowerMapper rolePowerMapper, UserPowerMapper userPowerMapper){
        this.usersMapper = usersMapper;
        this.rolePowerMapper = rolePowerMapper;
        this.userPowerMapper = userPowerMapper;
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        List<String> list = new ArrayList<>();

        QueryWrapper<RolePower> rolePowerQueryWrapper = new QueryWrapper<>();
        QueryWrapper<UserPower> userPowerQueryWrapper = new QueryWrapper<>();
        rolePowerQueryWrapper.eq("id", s);
        userPowerQueryWrapper.eq("role", o);
        List<RolePower> rolePowerList = rolePowerMapper.selectList(rolePowerQueryWrapper);
        List<UserPower> userPowerList = userPowerMapper.selectList(userPowerQueryWrapper);
        for (RolePower r : rolePowerList){
            String[] ss = r.getPower().split(",");
            list.addAll(Arrays.asList(ss));
        }
        for (UserPower u : userPowerList){
            String[] uu = u.getPower().split(",");
            list.addAll(Arrays.asList(uu));
        }

        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {

        QueryWrapper<Users> usersQueryWrapper = new QueryWrapper<>();
        usersQueryWrapper.eq("id", o);
        Users users = usersMapper.selectOne(usersQueryWrapper);

        return new ArrayList<>(Arrays.asList(users.getIdentifer().split(",")));
    }
}
