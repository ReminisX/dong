package com.zijin.dong.entity.vo;

import lombok.Data;

@Data
public class UserLoginVo {

    private String username;

    private String password;

    private boolean remember;

}
