package com.zijin.dong;

import com.zijin.dong.entity.wechat.*;
import com.zijin.dong.entity.wechat.login.LoginRec;
import com.zijin.dong.entity.wechat.login.LoginVo;
import com.zijin.dong.entity.wechat.paidunion.PaidUnionIdVo;
import com.zijin.dong.entity.wechat.paidunion.PaidUnionRec;
import com.zijin.dong.entity.wechat.pluginopen.PluginOpenPidRec;
import com.zijin.dong.entity.wechat.pluginopen.PluginOpenPidVo;
import com.zijin.dong.service.Impl.WeChatServerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeChatTest {

    @Autowired
    private WeChatServerImpl weChatServerImpl;

    @Test
    void loginTest(){
        // 登录测试
        LoginVo loginVo = new LoginVo();
        loginVo.setAppId("");
        loginVo.setGrantType("");
        loginVo.setJsCode("");
        loginVo.setSecret("");
        LoginRec loginRec = weChatServerImpl.login(loginVo);
        System.out.println(loginRec);
    }

    @Test
    void checkEncryptedDataTest(){
        String encryptedMsgHash = "";
        String token = "";
        EncryptedDataRec encryptedDataRec = weChatServerImpl.checkEncryptedData(encryptedMsgHash, token);
        System.out.println(encryptedDataRec);
    }

    @Test
    void getPaidUnionIdTest(){
        PaidUnionIdVo paidUnionIdVo = new PaidUnionIdVo();
        paidUnionIdVo.setAccess_token("");
        paidUnionIdVo.setOpenid("");
        paidUnionIdVo.setTransaction_id("");
        paidUnionIdVo.setMch_id("");
        paidUnionIdVo.setOut_trade_no("");
        PaidUnionRec paidUnionRec = weChatServerImpl.getPaidUnionId(paidUnionIdVo);
        System.out.println(paidUnionRec);
    }

    @Test
    void getPluginOpenPidTest(){
        PluginOpenPidVo pluginOpenPidVo = new PluginOpenPidVo();
        pluginOpenPidVo.setCode("0c2b206443ce0c23338f6cb4d1ec15a1af0b8bcddbff26510dda4b23a433c391");
        PluginOpenPidRec pluginOpenPidRec = weChatServerImpl.getPluginOpenPid("", pluginOpenPidVo);
        System.out.println(pluginOpenPidRec);
    }

    @Test
    void test(){

    }

}
