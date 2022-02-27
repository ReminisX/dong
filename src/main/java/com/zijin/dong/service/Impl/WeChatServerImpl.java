package com.zijin.dong.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.zijin.dong.entity.wechat.*;
import com.zijin.dong.entity.wechat.accesstoken.AccessTokenRec;
import com.zijin.dong.entity.wechat.accesstoken.AccessTokenVo;
import com.zijin.dong.entity.wechat.dailysummary.DailySummaryRec;
import com.zijin.dong.entity.wechat.dailysummary.DailySummaryVo;
import com.zijin.dong.entity.wechat.login.LoginRec;
import com.zijin.dong.entity.wechat.login.LoginVo;
import com.zijin.dong.entity.wechat.paidunion.PaidUnionIdVo;
import com.zijin.dong.entity.wechat.paidunion.PaidUnionRec;
import com.zijin.dong.entity.wechat.performdata.PerformDataRec;
import com.zijin.dong.entity.wechat.performdata.PerformDataVo;
import com.zijin.dong.entity.wechat.pluginopen.PluginOpenPidRec;
import com.zijin.dong.entity.wechat.pluginopen.PluginOpenPidVo;
import com.zijin.dong.entity.wechat.retain.RetainRec;
import com.zijin.dong.entity.wechat.retain.RetainVo;
import com.zijin.dong.entity.wechat.userportrait.UserPortraitRec;
import com.zijin.dong.entity.wechat.userportrait.UserPortraitVo;
import com.zijin.dong.entity.wechat.visitdistribution.VisitDistributionRec;
import com.zijin.dong.entity.wechat.visitdistribution.VisitDistributionVo;
import com.zijin.dong.entity.wechat.visittrend.VisitTrendRec;
import com.zijin.dong.entity.wechat.visittrend.VisitTrendVo;
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

    @Value("${wechat.paidUnionUrl}")
    private String paidUnionUrl;

    @Value("${wechat.pluginOpenPidUrl}")
    private String pluginOpenPidUrl;

    @Value("${wechat.accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${wechat.dailyRetainUrl}")
    private String dailyRetainUrl;

    @Value("${wechat.weekRetainUrl}")
    private String weekRetainUrl;

    @Value("${wechat.monthRetainUrl}")
    private String monthRetainUrl;

    @Value("${wechat.dailySummaryUrl}")
    private String dailySummaryUrl;

    @Value("${wechat.dailyVisitTrendUrl}")
    private String dailyVisitTrendUrl;

    @Value("${wechat.weekVisitTrendUrl}")
    private String weekVisitTrendUrl;

    @Value("${wechat.monthVisitTrendUrl}")
    private String monthVisitTrendUrl;

    @Value("${wechat.performDataUrl}")
    private String performDataUrl;

    @Value("${wechat.userPortraitUrl}")
    private String userPortraitUrl;

    @Value("${wechat.visitDistributionUrl}")
    private String visitDistributionUrl;

    @Value("${wechat.visitPageUrl}")
    private String visitPageUrl;

    // 全局accessToken，长度512
    private AccessTokenRec accessTokenRec;
    // accessToken失效时间
    private final Integer invaildTime = 2*60*60;

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
     * @param encryptedMsgHash 传递实体类
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

    /**
     * 用户支付完成后，获取该用户的 UnionId，无需用户授权
     * @param paidUnionIdVo 传递实体类
     * @return PaidUnionRec
     */
    public PaidUnionRec getPaidUnionId(PaidUnionIdVo paidUnionIdVo){
        Map<String, Object> paidUnionMap = BeanUtil.beanToMap(paidUnionIdVo);
        String paidUnionRecStr = HttpRequest.get(paidUnionUrl)
                .form(paidUnionMap)
                .execute()
                .body();
        return JSONUtil.toBean(paidUnionRecStr, PaidUnionRec.class);
    }

    /**
     * 通过 wx.pluginLogin 接口获得插件用户标志凭证 code 后传到开发者服务器，开发者服务器调用此接口换取插件用户的唯一标识 openpid。
     * @param pluginOpenPidVo 传递实体类
     * @return PluginOpenPidRec
     */
    public PluginOpenPidRec getPluginOpenPid(String accessToken, PluginOpenPidVo pluginOpenPidVo){
        String pluginOpenPidRecStr = HttpRequest.post(pluginOpenPidUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(pluginOpenPidVo))
                .execute()
                .body();
        return JSONUtil.toBean(pluginOpenPidRecStr, PluginOpenPidRec.class);
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据，该token有效期2小时，该函数设置缓存避免多次查询
     * @param accessTokenVo 传递实体类
     * @return AccessTokenRec
     */
    public AccessTokenRec getAccessToken(AccessTokenVo accessTokenVo){
        DateTime now = new DateTime();
        if (accessTokenRec == null || accessTokenRec.getLastUpdate() == null || DateUtil.between(accessTokenRec.getLastUpdate(), now, DateUnit.SECOND) >= invaildTime) {
            Map<String, Object> accessTokenMap = BeanUtil.beanToMap(accessTokenVo);
            String accessTokenRecStr = HttpRequest.get(accessTokenUrl)
                    .form(accessTokenMap)
                    .execute()
                    .body();
            this.accessTokenRec = JSONUtil.toBean(accessTokenRecStr, AccessTokenRec.class);
        }
        Long timeDiff = DateUtil.between(now, accessTokenRec.getLastUpdate(), DateUnit.SECOND);
        accessTokenRec.setExpire_in(accessTokenRec.getExpire_in() - timeDiff);
        accessTokenRec.setLastUpdate(now);
        return accessTokenRec;
    }

    /**
     * 获取用户访问小程序留存
     * @param retainVo 传递实体类
     * @param type 获取访问类型（day、week、month）
     * @return DaliyRetainRec
     */
    public RetainRec getRetain(RetainVo retainVo, String type, String accessToken){
        String retainUrl;
        switch (type){
            case "day":
                retainUrl = dailyRetainUrl;
                break;
            case "week":
                retainUrl = weekRetainUrl;
                break;
            case "month":
                retainUrl = monthRetainUrl;
                break;
            default:
                logger.error("输入类型错误");
                throw new IllegalArgumentException("输入类型错误");
        }
        String daliyRetainRecStr = HttpRequest.post(retainUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(retainVo))
                .execute()
                .body();
        return JSONUtil.toBean(daliyRetainRecStr, RetainRec.class);
    }

    /**
     * 获取用户小程序数据概况
     * @param dailySummaryVo 传递实体类
     * @param accessToken token
     * @return DailySummaryRec
     */
    public DailySummaryRec getDailySummary(DailySummaryVo dailySummaryVo, String accessToken){
        String dailySummaryRecStr = HttpRequest.post(dailySummaryUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(dailySummaryVo))
                .execute()
                .body();
        return BeanUtil.toBean(dailySummaryRecStr, DailySummaryRec.class);
    }

    /**
     * 用户访问小程序趋势
     * @param visitTrendVo 实体类
     * @param type 调用类型（日、周、月）
     * @param accessToken token
     * @return VisitTrendRec
     */
    public VisitTrendRec getVisitTrend(VisitTrendVo visitTrendVo, String type, String accessToken){
        String visitTrendUrl;
        switch (type){
            case "day":
                visitTrendUrl = dailyVisitTrendUrl;
                break;
            case "week":
                visitTrendUrl = weekVisitTrendUrl;
                break;
            case "month":
                visitTrendUrl = monthVisitTrendUrl;
                break;
            default:
                logger.error("输入类型错误");
                throw new IllegalArgumentException("输入类型错误");
        }
        String visitTrendRecStr = HttpRequest.post(visitTrendUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(visitTrendVo))
                .execute()
                .body();
        return JSONUtil.toBean(visitTrendRecStr, VisitTrendRec.class);
    }

    /**
     * 获取小程序启动性能，运行性能等数据。
     * @param performDataVo 请求数据实体类
     * @param accessToken token
     * @return PerFormDataRec
     */
    public PerformDataRec getPerformData(PerformDataVo performDataVo, String accessToken){
        String performDataRecStr = HttpRequest.post(performDataUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(performDataVo))
                .execute()
                .body();
        return BeanUtil.toBean(performDataRecStr, PerformDataRec.class);
    }

    /**
     * 用户画像获取
     * @param userPortraitVo 用户画像时间段
     * @param accessToken token
     * @return UserPortraitRec
     */
    public UserPortraitRec getUserPortrait(UserPortraitVo userPortraitVo, String accessToken){
        String userPortraitRecStr = HttpRequest.post(userPortraitUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(userPortraitVo))
                .execute()
                .body();
        return BeanUtil.toBean(userPortraitRecStr, UserPortraitRec.class);
    }

    /**
     * 用户访问分布数据
     * @param visitDistributionVo 上传分布数据
     * @param accessToken token
     * @return VisitDistributionRec
     */
    public VisitDistributionRec getVisitDistribution(VisitDistributionVo visitDistributionVo, String accessToken){
        String visitDistributionRecStr = HttpRequest.post(visitDistributionUrl)
                .form("access_token", accessToken)
                .body(JSONUtil.toJsonStr(visitDistributionVo))
                .execute()
                .body();
        return BeanUtil.toBean(visitDistributionRecStr, VisitDistributionRec.class);
    }

}
