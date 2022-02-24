package com.zijin.dong.utils;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Tools {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public Tools(RequestMappingHandlerMapping requestMappingHandlerMapping){
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    public Set<String> getAllUrl() throws IOException {
        // url与方法的对应关系
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<String> urls = Sets.newHashSet();
        handlerMethods.keySet().forEach(handlerMethod -> urls.addAll(handlerMethod.getPatternsCondition().getPatterns()));

        String path = "C:\\Users\\ZhangXD\\Desktop\\urls.txt";
        File f = new File(path);
        FileWriter fileWriter = new FileWriter(f);
        for (String url : urls){
            fileWriter.write(url + "\n");
        }
        fileWriter.close();
        System.out.println(urls);
        return urls;
    }

}
