package com.zijin.dong.controller;

import com.zijin.dong.entity.base.Pages;
import com.zijin.dong.entity.base.Paging;
import com.zijin.dong.entity.Users;
import com.zijin.dong.entity.base.BaseResponse;
import com.zijin.dong.entity.vo.RegisterUserVo;
import com.zijin.dong.service.AdminService;
import com.zijin.dong.service.AreaService;
import com.zijin.dong.service.UsersService;
import com.zijin.dong.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/root")
public class RootController {

    private final AdminService adminServiceImpl;

    private final UsersService usersServiceImpl;

    private final AreaService areaServiceImpl;

    @Autowired
    public RootController(AdminService adminServiceImpl, UsersService usersServiceImpl, AreaService areaServiceImpl) {
        this.adminServiceImpl = adminServiceImpl;
        this.usersServiceImpl = usersServiceImpl;
        this.areaServiceImpl = areaServiceImpl;
    }

    @PostMapping("/getAllUser")
    public BaseResponse getAllUser(@RequestBody Paging paging){
        Pages<Users> res = adminServiceImpl.getAllUser(paging);
        if (res.getTotalCount() <= 0){
            return ResponseUtil.faliure().setMessage("查询列表为空");
        }else{
            return ResponseUtil.success().addData(res);
        }
    }

    @PostMapping("/register")
    public BaseResponse register(@RequestBody RegisterUserVo registerUserVo){
        boolean res = usersServiceImpl.addUser(registerUserVo);
        return res ? ResponseUtil.success() : ResponseUtil.faliure().addParam("message", "重复创建用户");
    }

    @GetMapping("/getAllRegion")
    public BaseResponse getAllRegion() {
        List<String> res = areaServiceImpl.getAllRegion();
        if (Objects.isNull(res) || res.size() == 0){
            return ResponseUtil.faliure();
        }else{
            return ResponseUtil.success().addParam("regions", res);
        }
    }

}
