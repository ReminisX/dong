package com.zijin.dong.service;

import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.entity.Users;

public interface AdminService {

    Pages<Users> getAllUser(Paging paging);

    boolean logout(Long id);

    boolean kickout(Long id);

    boolean forbidden(Long id, Integer day);

}
