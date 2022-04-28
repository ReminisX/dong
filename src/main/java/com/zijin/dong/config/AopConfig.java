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

        Signature signature = joinPoint.getSignature();
        System.out.println("signature:" + signature);
        System.out.println(signature.toLongString());
        // 切分参数类型
        String signatureStr = signature.toLongString();
        int index = 0;
        for (int i = 0; i < signatureStr.length(); i++) {
            if (signatureStr.charAt(i) == '('){
                index = i;
            }
        }
        String[] argsClasses = signatureStr.substring(index+1, signatureStr.length()-1).split(",");
        System.out.println(Arrays.toString(argsClasses));
        // 参数类型判断
        for(String argsClass : argsClasses){
            System.out.println(String.class);
            if (String.class.toString().equals(argsClass)){
                System.out.println("success");
            }
        }



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
//        String functionName = joinPoint.getSignature().getName();
//        String log = functionName + ": 返回的参数为[" + returnValue + "]";
//        logger.info(log);
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
