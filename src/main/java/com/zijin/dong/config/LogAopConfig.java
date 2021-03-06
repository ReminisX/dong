package com.zijin.dong.config;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zijin.dong.annotation.LogAnnotation;
import com.zijin.dong.component.UserComponent;
import com.zijin.dong.entity.UserLogs;
import com.zijin.dong.mapper.UserLogsMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Aspect
@Component
public class LogAopConfig {

    private final UserLogsMapper userLogsMapper;

    private final UserComponent userComponent;

    private final ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(LogAopConfig.class);

    @Autowired
    public LogAopConfig(UserLogsMapper userLogsMapper, UserComponent userComponent, ObjectMapper mapper) {
        this.userLogsMapper = userLogsMapper;
        this.userComponent = userComponent;
        this.mapper = mapper;
    }

    @Around(value = "@annotation(logAnnotation)")
    public Object logMethod(ProceedingJoinPoint joinPoint, LogAnnotation logAnnotation) throws JsonProcessingException {
        // 获取用户名
        Long id = null;
        try {
            id = StpUtil.getLoginIdAsLong();
        } catch (Exception e) {
            logger.warn("先序获取name失败，延后处理");
        }
        // 获取请求参数，构造入参日志
        Object[] args = joinPoint.getArgs();
        String inputLog = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(args);
        // 执行代理函数
        Object returnValue = null;
        try {
            returnValue = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("执行体函数执行异常");
            throwable.printStackTrace();
        }
        // 获取返回参数，构造返回体日志
        String returnLog = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(returnValue);
        // 若当前id为空，再次尝试获取
        if (Objects.isNull(id)) {
            try {
                id = StpUtil.getLoginIdAsLong();
            } catch (Exception e) {
                logger.error("后序获取id失败");
                id = 0L;
            }
        }
        // 生成日志
        String name = userComponent.getNameById(id);
        String logMessage = logAnnotation.value();
        UserLogs userLogs = new UserLogs();
        userLogs.setUsername(name);
        userLogs.setOperation(logMessage);
        userLogs.setTime(new Date());
        userLogs.setInput(inputLog);
        userLogs.setResult(returnLog);
        userLogsMapper.insert(userLogs);
        logger.info("用户【" + name + "】操作记录为：" + logMessage);
        return returnValue;
    }

}
