package com.zijin.dong.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.vo.RegisterUserVo;
import com.zijin.dong.entity.vo.UserInfoVo;
import com.zijin.dong.entity.vo.UserLoginVo;
import com.zijin.dong.mapper.UsersMapper;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.MinioUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

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

    private final MinioUtil minioUtil;

    private final String DEFAULT_HEAD_AVATAR = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper, MinioUtil minioUtil){
        this.usersMapper = usersMapper;
        this.minioUtil = minioUtil;
    }

    /**
     * 注册新用户
     * @param registerUserVo 用户实体
     * @return 是否成功
     */
    @Override
    @Transactional
    public boolean addUser(RegisterUserVo registerUserVo){
        long id = IdUtil.getSnowflakeNextId();
        Users user = new Users();
        // 查询是否存在该id
        QueryWrapper<Users> selectWrapper = new QueryWrapper<>();
        selectWrapper.eq("username", registerUserVo.getName());
        selectWrapper.select("id");
        Users isExist = usersMapper.selectOne(selectWrapper);
        if (!Objects.isNull(isExist)){
            return false;
        }
        // 插入该对象
        user.setId(id);
        user.setUsername(registerUserVo.getName());
        user.setCreateTime(new Date());
        user.setPassword(registerUserVo.getPass());
        user.setPower(registerUserVo.getPower());
        user.setHeadImg(registerUserVo.getImgUrl());
        user.setStatus("离线");
        int res = usersMapper.insert(user);
        if (res != 0){
            logger.info("用户[" + user.getUsername() + "]添加成功");
            return true;
        }else{
            logger.warn("用户[" + user.getUsername() + "]添加失败");
            return false;
        }
    }

    @Override
    public UserInfoVo getUserInfo(String token) {
        UserInfoVo userInfoVo = new UserInfoVo();
        // 用户数据库信息
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        Users users = usersMapper.selectOne(queryWrapper);
        String username = users.getUsername();
        // 用户头像获取
        String avatar = DEFAULT_HEAD_AVATAR;
        if (!Objects.isNull(users.getHeadImg()) && users.getHeadImg().length() > 0) {
            avatar = minioUtil.getObjectUrl("head-portrait", users.getHeadImg());
        }else {
            logger.warn("用户头像信息为空，使用默认头像");
        }
        // 用户信息整合
        userInfoVo.setUsername(username);
        userInfoVo.setHeadPortrait(avatar);
        return userInfoVo;
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
            user.setStatus("在线");
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
            user.setStatus("离线");
            usersMapper.updateById(user);
            logger.info("[" + id + "]下线");
            StpUtil.logout();
        }catch (Exception e){
            logger.warn(e.getLocalizedMessage());
        }
        return id != null;
    }

    @Override
    @Transactional
    public boolean uploadHead(MultipartFile multipartFile, String name) {
        String bucket = "head-portrait";
        String[] fileNames = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.");
        String fileType = fileNames[fileNames.length-1];
        String fileName = name + "-head-portrait." + fileType;
        try {
            minioUtil.upload(bucket, fileName, multipartFile.getInputStream());
            Users user = new Users();
            user.setUsername(name);
            user.setHeadImg(fileName);
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", name);
            usersMapper.update(user, queryWrapper);
        } catch (IOException e) {
            logger.error("头像上传失败");
            return false;
        }
        return true;
    }
}




