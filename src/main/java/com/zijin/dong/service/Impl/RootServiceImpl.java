package com.zijin.dong.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.entity.Users;
import com.zijin.dong.mapper.UsersMapper;
import com.zijin.dong.service.RootService;
import com.zijin.dong.entity.base.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RootServiceImpl implements RootService {

    private final UsersMapper usersMapper;

    @Autowired
    public RootServiceImpl(UsersMapper usersMapper){
        this.usersMapper = usersMapper;
    }

    @Override
    public Pages<Users> getAllUser(Paging paging) {
        Pages<Users> pages;
        if (paging != null && paging.getPage() != null && paging.getNum() != null){
            IPage<Users> userIPage = new Page(paging.getPage(), paging.getNum());
            userIPage = usersMapper.selectPage(userIPage, null);
            pages = new Pages<>(userIPage);
        }else{
            List<Users> list = usersMapper.selectList(null);
            pages = new Pages<>(list);
        }
        return pages;
    }

    @Override
    public boolean logout(Long id) {

        return false;
    }

    @Override
    public boolean kickout(Long id) {
        return false;
    }

    @Override
    public boolean forbidden(Long id, Integer day) {
        return false;
    }
}
