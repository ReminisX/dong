package com.zijin.dong.auto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

@Component
public class BackUpSql {

    private final Logger logger = LoggerFactory.getLogger(BackUpSql.class);

    @Value("${autosql.filepath}")
    private String sqlFilePath;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Value("${autosql.name}")
    private String database;

    /**
     * 数据库自动备份
     * 每天凌晨2点自动备份
     */
    @Scheduled(cron = "0 0 2 1/1 * ? ")
    public void backUpMySql(){
        String date = DateUtil.formatDate(new Date());
        String sqlFileName = database + "-" + date + ".sql";
        StringBuilder cmd = new StringBuilder();
        cmd.append("mysqldump");
        cmd.append(" -u").append(username);
        cmd.append(" -p").append(password);
        cmd.append(" ").append(database);
        cmd.append(" > ").append(sqlFilePath).append(File.separator).append(sqlFileName);
        logger.info("数据库开始备份");
        String res = RuntimeUtil.execForStr(cmd.toString());
        logger.info("数据库备份完成，返回信息" + res);
    }

}
