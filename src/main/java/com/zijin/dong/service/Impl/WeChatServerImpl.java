package com.zijin.dong.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.zijin.dong.entity.wechat.EncryptedDataRec;
import com.zijin.dong.entity.wechat.LoginRec;
import com.zijin.dong.entity.wechat.LoginVo;
import com.zijin.dong.service.WeChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatServerImpl implements WeChatServer {

    private final Logger logger = LoggerFactory.getLogger(WeChatServerImpl.class);

    @Value("${wechat.loginUrl}")
    private String loginUrl;

    @Value("${wechat.checkEncryptedDataUrl}")
    private String checkEncryptedDataUrl;

    /**
     * 微信登录凭证校验
     * @param loginVo 微信登录传递参数
     * @return loginRecVo 微信返回值
     */
    public LoginRec login(LoginVo loginVo) {
        Map<String, Object> loginVoMap = BeanUtil.beanToMap(loginVo);
        logger.info("发送http请求中");
        String loginRecStr = HttpRequest.get(loginUrl)
                .form(loginVoMap)
                .execute()
                .body();
        logger.info("接受到的数据为: " + loginRecStr);
        return JSONUtil.toBean(loginRecStr, LoginRec.class);
    }


    /**
     * 检查加密信息是否由微信生成
     * @param encryptedMsgHash
     * @return EncryptedDataRecVo
     */
    public EncryptedDataRec checkEncryptedData(String encryptedMsgHash, String token){
        Map<String, Object> encryptedMap = new HashMap<>();
        encryptedMap.put("access_token", token);
        String encryptedMsgHashJson = JSONUtil.formatJsonStr("{\"encrypted_msg_hash\": \""+ encryptedMsgHash +"\"}");
        String encryptedDataRecStr = HttpRequest.post(checkEncryptedDataUrl)
                .form(encryptedMap)
                .body(encryptedMsgHashJson)
                .execute()
                .body();
        return JSONUtil.toBean(encryptedDataRecStr, EncryptedDataRec.class);
    }


    public void getPaidUnionId(){

    }
}
