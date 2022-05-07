package com.zijin.dong.service;

import com.zijin.dong.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zijin.dong.entity.vo.RegisterUserVo;
import com.zijin.dong.entity.vo.UserInfoVo;
import com.zijin.dong.entity.vo.UserLoginVo;
import org.springframework.web.multipart.MultipartFile;

/**
* @author ZhangXD
* @description 针对表【users】的数据库操作Service
* @createDate 2022-02-22 16:05:07
*/
public interface UsersService extends IService<Users> {

    boolean addUser(RegisterUserVo registerUserVo);

    UserInfoVo getUserInfo(String token);

    Long login(UserLoginVo userLoginVo);

    boolean exit();

    String uploadHead(MultipartFile multipartFile);
}
