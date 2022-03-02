package com.zijin.dong.service.Impl;

import com.zijin.dong.entity.MyFile;
import com.zijin.dong.mapper.MyFileMapper;
import com.zijin.dong.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final MyFileMapper myFileMapper;

    private final Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);

    @Value("${component.fileSavePath}")
    private String fileSavePath;

    @Autowired
    public ComponentServiceImpl(MyFileMapper myFileMapper) {
        this.myFileMapper = myFileMapper;
    }

    @Override
    @Transactional
    public String saveFile(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile)){
            logger.error("未获取到上传文件");
            return null;
        }
        String fileName = multipartFile.getOriginalFilename();
        String path = fileSavePath + "\\" + fileName;
        File f = new File(path);
        try(InputStream in = multipartFile.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(f)) {
            byte[] b = new byte[1024];
            while(in.read(b) != -1){
                fileOutputStream.write(b);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        MyFile myFile = new MyFile();
        myFile.setFileName(fileName);
        myFile.setFilePath(path);
        myFileMapper.insert(myFile);
        logger.info(multipartFile.getOriginalFilename() + "文件上传成功");
        return path;
    }

    @Override
    public boolean delFile(String fileName) {
        
        return false;
    }

    @Override
    public boolean delFileByPath(String path) {
        return false;
    }
}
