package com.zijin.dong.service;

import com.zijin.dong.entity.base.DtoBase;
import org.springframework.web.multipart.MultipartFile;

public interface ComponentService {

    DtoBase saveFile(MultipartFile multipartFile);

    boolean delFile(String fileName);

    boolean delFileByPath(String path);

}
