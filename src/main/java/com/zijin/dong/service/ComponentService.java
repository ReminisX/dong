package com.zijin.dong.service;

import org.springframework.web.multipart.MultipartFile;

public interface ComponentService {

    String saveFile(MultipartFile multipartFile);

    boolean delFile(String fileName);

    boolean delFileByPath(String path);

}
