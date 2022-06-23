package com.zijin.dong.service;

import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.entity.vo.UserManagerVo;

public interface AdminService {

    Pages<UserManagerVo> getAllUser(Paging paging);

    boolean logout(Long id);

    boolean kickout(Long id);

    boolean forbidden(Long id, Integer day);

}
