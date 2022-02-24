package com.zijin.dong.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SaTokenConfig implements WebMvcConfigurer {
    // 注册Sa-Token的注解拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaRouteInterceptor((saRequest, saResponse, o) -> {
            // 拦截所有请求，仅开放login接口
//            SaRouter.match("/**", "/login/login", r -> StpUtil.checkLogin());
            // 配置路由权限
            SaRouter.match("/root").check(r -> StpUtil.checkRole("root"));
            SaRouter.match("/login").check(r -> StpUtil.checkRoleOr("root", "admin", "normal"));
            SaRouter.notMatch("/doc.html");
        })).addPathPatterns("/**");
    }
}

