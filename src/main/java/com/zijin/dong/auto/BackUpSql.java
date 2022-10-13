package com.zijin.dong.auto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
@ConditionalOnProperty(prefix = "autosql", name = "enable", havingValue = "true")
public class BackUpSql {

    private final Logger logger = LoggerFactory.getLogger(BackUpSql.class);

    @Value("${autosql.windowsFilepath}")
    private String windowsFilePath;

    @Value("${autosql.linuxFilePath}")
    private String linuxFilePath;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Value("${autosql.name}")
    private String database;

    public BackUpSql() {
        logger.info("自动备份任务初始化完成");
    }

    /**
     * 数据库自动备份
     * 每天凌晨2点自动备份
     */
    @Scheduled(cron = "0 0 2 1/1 * ? ")
    public void backUpMySql(){
        String path = System.getProperties().getProperty("os.name").equalsIgnoreCase("linux") ? linuxFilePath : windowsFilePath;
        String date = DateUtil.formatDate(new Date());
        String sqlFileName = database + "-" + date + ".sql";
        String cmd = "mysqldump" +
                " -u" + username +
                " -p" + password +
                " " + database +
                " > " + path + File.separator + sqlFileName;
        logger.info("数据库开始备份");
        String res = RuntimeUtil.execForStr(cmd);
        logger.info("数据库备份完成，返回信息" + res);
    }

}
