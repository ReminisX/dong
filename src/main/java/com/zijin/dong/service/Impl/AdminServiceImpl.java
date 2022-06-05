package com.zijin.dong.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.entity.vo.UserManagerVo;
import com.zijin.dong.mapper.UsersMapper;
import com.zijin.dong.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final UsersMapper usersMapper;

    private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    public AdminServiceImpl(UsersMapper usersMapper){
        this.usersMapper = usersMapper;
    }

    /**
     * 获取全部用户信息
     * @param paging
     * @return
     */
    @Override
    public Pages<UserManagerVo> getAllUser(Paging paging) {
        List<Users> list;
        int totalCount = 0;
        int pageSize = 0;
        int currPage = 0;
        int totalPage = 0;
        if (paging != null && paging.getPage() != null && paging.getNum() != null){
            IPage<Users> userIPage = new Page(paging.getPage(), paging.getNum());
            userIPage = usersMapper.selectPage(userIPage, null);
            list = userIPage.getRecords();
            totalCount = (int)userIPage.getTotal();
            pageSize = (int)userIPage.getSize();
            currPage = (int)userIPage.getCurrent();
            totalPage = (int)userIPage.getPages();
        }else{
            list = usersMapper.selectList(null);
        }
        List<UserManagerVo> userManagerVos = new ArrayList<>();
        for(Users u : list){
            UserManagerVo userManagerVo = new UserManagerVo();
            BeanUtil.copyProperties(u, userManagerVo);
            userManagerVos.add(userManagerVo);
        }
        return new Pages<UserManagerVo>(userManagerVos, totalCount, pageSize, currPage, totalPage);
    }

    /**
     * 注销id
     * @param id
     * @return
     */
    @Override
    public boolean logout(Long id) {
        boolean res = false;
        try{
            StpUtil.logout(id);
            logger.info("账号[" + id + "]已注销");
            res = true;
        }catch (Exception e){
            logger.warn("账号[" + id + "]注销失败");
            logger.warn(e.getLocalizedMessage());
        }
        return res;
    }

    /**
     * 踢人下线
     * @param id
     * @return
     */
    @Override
    public boolean kickout(Long id) {
        boolean res = false;
        try{
            StpUtil.kickout(id);
            logger.info("账号[" + id + "]已下线");
            res = true;
        }catch (Exception e){
            logger.warn("账号[" + id + "]下线失败");
            logger.warn(e.getLocalizedMessage());
        }
        return res;
    }

    /**
     * 封禁账号
     * @param id 账号id
     * @param hour 封禁时间（小时）
     * @return
     */
    @Override
    public boolean forbidden(Long id, Integer hour) {
        boolean res = false;
        try{
            Users user = new Users();
            user.setForbid(new Date());
            usersMapper.update(user, new QueryWrapper<Users>().eq("id", id));
            StpUtil.disable(id, hour * 60 * 60);
            logger.info("账号[" + id + "]封禁" + hour + "小时");
            res = true;
        }catch (Exception e){
            logger.warn("账号[" + id + "]封禁失败");
            logger.warn(e.getLocalizedMessage());
        }
        return res;
    }
}
