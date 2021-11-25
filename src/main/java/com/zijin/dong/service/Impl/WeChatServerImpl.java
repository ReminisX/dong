package com.zijin.dong.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.zijin.dong.entity.vo.LoginRecVo;
import com.zijin.dong.entity.vo.LoginVo;
import com.zijin.dong.service.WeChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeChatServerImpl implements WeChatServer {

    private final Logger logger = LoggerFactory.getLogger(WeChatServerImpl.class);

    @Value("${login.url}")
    private String loginUrl;

    /**
     * 微信登录凭证校验
     * @param loginVo 微信登录传递参数
     * @return loginRecVo 微信返回值
     */
    public LoginRecVo login(LoginVo loginVo) {
        Map<String, Object> loginVoMap = BeanUtil.beanToMap(loginVo);
        logger.info("发送http请求中");
        String loginRecStr = HttpRequest.get(loginUrl)
                .form(loginVoMap)
                .execute() 
                .body();
        logger.info("接受到的数据为: " + loginRecStr);
        return JSONUtil.toBean(loginRecStr, LoginRecVo.class);
    }

    public void checkEncryptedData(){

    }
}
