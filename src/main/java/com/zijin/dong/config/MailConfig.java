package com.zijin.dong.config;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Bean
    public MailAccount getMailAccount(){
        MailAccount account = new MailAccount();
        // 邮件服务器的SMTP地址，可选，默认为smtp.<发件人邮箱后缀>
        account.setHost("");
        // 邮件服务器的SMTP端口，可选，默认25
        account.setPort(25);
        // 是否需要用户名密码验证
        account.setAuth(true);
        // 发件人（必须正确，否则发送失败）
        account.setFrom("");
        // 用户名，默认为发件人邮箱前缀
        account.setUser("");
        // 密码（注意，某些邮箱需要为SMTP服务单独设置授权码，详情查看相关帮助）
        account.setPass("");
        return account;
    }

}
