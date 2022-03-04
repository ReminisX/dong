package com.zijin.dong.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.zijin.dong.entity.RolePower;
import com.zijin.dong.mapper.RolePowerMapper;
import com.zijin.dong.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class SaTokenConfig implements WebMvcConfigurer {

    private final RolePowerMapper rolePowerMapper;

    private final UsersMapper usersMapper;

    @Autowired
    public SaTokenConfig(RolePowerMapper rolePowerMapper, UsersMapper usersMapper) {
        this.rolePowerMapper = rolePowerMapper;
        this.usersMapper = usersMapper;
    }

    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<RolePower> rolePowerList = rolePowerMapper.selectList(null);

        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaRouteInterceptor((saRequest, saResponse, o) -> {
            // 拦截所有请求，仅开放login接口
//            SaRouter.match("/**", "/login/login", r -> StpUtil.checkLogin());
            for (RolePower rolePower : rolePowerList) {
                SaRouter.match(rolePower.getPower()).check(r -> StpUtil.checkRole(rolePower.getRole()));
            }
        })).addPathPatterns("/**");
    }
}

