package com.zijin.dong.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserManagerVo {

    private String username;

    private String power;

    private Date loginTime;

    private String status;

}
