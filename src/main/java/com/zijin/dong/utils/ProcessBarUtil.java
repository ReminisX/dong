package com.zijin.dong.utils;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

public class ProcessBarUtil {

    private static String processName;

    private static Integer sumSize;

    private static Integer now;

    private static Integer speed;

    private static int length = 0;

    public ProcessBarUtil(String processName, Integer sumSize, Integer now, Integer speed){
        ProcessBarUtil.processName = processName;
        ProcessBarUtil.sumSize = sumSize;
        ProcessBarUtil.now = now;
        ProcessBarUtil.speed = speed;
    }

    public static void setData(String processName, Integer sumSize, Integer now, Integer speed){
        ProcessBarUtil.processName = processName;
        ProcessBarUtil.sumSize = sumSize;
        ProcessBarUtil.now = now;
        ProcessBarUtil.speed = speed;
    }

    public static void show(){
        int l = 20;
        int load = now * l / sumSize;
        int noLoad = l - load;

        StringBuilder sb = new StringBuilder();
        sb.append(processName).append(":");
        sb.append("[");
        for (int i = 0; i < load; i++) {
            sb.append("=");
        }
        for (int i = 0; i < noLoad; i++) {
            sb.append(" ");
        }
        sb.append("]");
        System.out.print(sb);
        length = sb.length();
    }

    public static void clear(){
        for (int i = 0; i < length; i++) {
            System.out.print("\b");
        }
    }

    public static void showBar(String processName, Integer sumSize, Integer now, Integer speed){
        clear();
        setData(processName, sumSize, now, speed);
        show();
    }
}
