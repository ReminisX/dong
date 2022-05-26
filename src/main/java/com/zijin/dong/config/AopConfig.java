package com.zijin.dong.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.Arrays;

@Aspect
@Component
public class AopConfig {

    private final Logger logger = LoggerFactory.getLogger(AopConfig.class);

    @Pointcut("execution(public * com.zijin.dong.controller.*.*(..))")
    public void controllerLog(){}

    @Pointcut("execution(public * com.zijin.dong.service.Impl.*.*(..))")
    public void serviceLog(){}

    @Pointcut("execution(public * com.zijin.dong.DongApplication.*.*(..))")
    public void testLog(){}

    @Before("controllerLog()")
    public void beforeController(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String functionName = joinPoint.getSignature().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(functionName);
        sb.append(": 收到的参数为[");
        for (Object obj: args) {
            sb.append(obj + ",");
        }
        sb.delete(sb.length()-1, sb.length());
        sb.append("]");
        logger.info(sb.toString());
    }

    @AfterReturning(value = "controllerLog()", returning = "returnValue")
    public void afterController(JoinPoint joinPoint, Object returnValue){
        Object[] args = joinPoint.getArgs();
        String functionName = joinPoint.getSignature().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(functionName);
        sb.append(": 收到的参数为[");
        for (Object obj: args) {
            sb.append(obj + ",");
        }
        sb.delete(sb.length()-1, sb.length());
        sb.append("]");
        logger.info(sb.toString());
        String log = functionName + ": 返回的参数为[" + returnValue + "]";
        logger.info(log);
    }

    @AfterReturning(value = "testLog()", returning = "returnValue")
    public void afterTest(JoinPoint joinPoint, Object returnValue){
        Object[] args = joinPoint.getArgs();
        String functionName = joinPoint.getSignature().getName();
        StringBuilder sb = new StringBuilder();
        sb.append(functionName);
        sb.append(": 收到的参数为[");
        for (Object obj: args) {
            sb.append(obj + ",");
        }
        sb.delete(sb.length()-1, sb.length());
        sb.append("]");
        logger.info(sb.toString());
        String log = functionName + ": 返回的参数为[" + returnValue + "]";
        logger.info(log);
    }
}
