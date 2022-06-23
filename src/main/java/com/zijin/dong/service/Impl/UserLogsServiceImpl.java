package com.zijin.dong.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.UserLogs;
import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.mapper.UserLogsMapper;
import com.zijin.dong.service.UserLogsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Reminis
* @description 针对表【user_logs】的数据库操作Service实现
* @createDate 2022-05-28 16:37:12
*/
@Service
public class UserLogsServiceImpl extends ServiceImpl<UserLogsMapper, UserLogs>
    implements UserLogsService{

    private final UserLogsMapper userLogsMapper;

    public UserLogsServiceImpl(UserLogsMapper userLogsMapper) {
        this.userLogsMapper = userLogsMapper;
    }

    @Override
    public Pages<UserLogs> getAllLogs(Paging paging) {
        Pages<UserLogs> pages;
        if (paging != null && paging.getPage() != null && paging.getNum() != null){
            IPage<UserLogs> userIPage = new Page(paging.getPage(), paging.getNum());
            userIPage = userLogsMapper.selectPage(userIPage, null);
            pages = new Pages<>(userIPage);
        }else{
            List<UserLogs> list = userLogsMapper.selectList(null);
            pages = new Pages<>(list);
        }
        return pages;
    }
}




