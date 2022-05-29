package com.zijin.dong.config;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.annotation.LogAnnotation;
import com.zijin.dong.entity.UserLogs;
import com.zijin.dong.entity.Users;
import com.zijin.dong.mapper.UserLogsMapper;
import com.zijin.dong.mapper.UsersMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.util.Objects;

@Aspect
@Component
public class LogAopConfig {

    private final Jedis jedis;

    private final UsersMapper usersMapper;

    private final UserLogsMapper userLogsMapper;

    private final Logger logger = LoggerFactory.getLogger(LogAopConfig.class);

    @Autowired
    public LogAopConfig(JedisPool jedisPool, UsersMapper usersMapper, UserLogsMapper userLogsMapper) {
        this.jedis = jedisPool.getResource();
        this.usersMapper = usersMapper;
        this.userLogsMapper = userLogsMapper;
    }

    @Pointcut("@annotation(com.zijin.dong.annotation.LogAnnotation)")
    public void myLogAspect(){}

    @Before(value = "myLogAspect() && @annotation(logAnnotation)")
    public void logMethod(LogAnnotation logAnnotation) {
        Long id = StpUtil.getLoginIdAsLong();
        String name = jedis.get(String.valueOf(id));
        // 若redis中无数据，则从数据库中查询
        if (Objects.isNull(name) || name.length() <= 0) {
            QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            queryWrapper.select("username");
            Users user = usersMapper.selectOne(queryWrapper);
            name = user.getUsername();
        }
        // 若数据库中无数据，则报错;否则添加redis缓存
        if (Objects.isNull(name) || name.length() <= 0) {
            logger.error("操作日志记录错误，未查询到该id");
            return;
        }else {
            jedis.append(String.valueOf(id), name);
        }
        // 生成日志
        String logMessage = logAnnotation.value();
        UserLogs userLogs = new UserLogs();
        userLogs.setUsername(name);
        userLogs.setOperation(logMessage);
        userLogs.setTime(LocalDateTime.now());
        userLogsMapper.insert(userLogs);
        logger.info("用户【" + name + "】操作记录为：" + logMessage);
    }

}
