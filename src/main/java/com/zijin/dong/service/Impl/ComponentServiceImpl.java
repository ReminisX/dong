package com.zijin.dong.service.Impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zijin.dong.entity.MyFile;
import com.zijin.dong.entity.base.DtoBase;
import com.zijin.dong.mapper.MyFileMapper;
import com.zijin.dong.service.ComponentService;
import com.zijin.dong.utils.DtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
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
    public DtoBase saveFile(MultipartFile multipartFile) {
        String msg;
        if (Objects.isNull(multipartFile)){
            msg = "未获取到上传文件";
            logger.error(msg);
            return DtoUtil.dto().setMessage(msg).setCode("501");
        }
        String fileName = multipartFile.getOriginalFilename();
        String path = fileSavePath + "\\" + fileName;
        File f = new File(path);
        String shaHex = "";
        QueryWrapper<MyFile> queryWrapper = new QueryWrapper<>();
        try(InputStream in = multipartFile.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(f)) {
            shaHex = DigestUtil.sha256Hex(in);
            queryWrapper.eq("hex", shaHex);
            MyFile test = myFileMapper.selectOne(queryWrapper);
            if (!Objects.isNull(test)){
                msg = "文件重复上传";
                logger.warn(msg);
                return DtoUtil.dto().setCode("502").setMessage(msg);
            }
            byte[] b = new byte[1024];
            while(in.read(b) != -1){
                fileOutputStream.write(b);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        MyFile myFile = new MyFile();
        myFile.setHex(shaHex);
        myFile.setFileName(fileName);
        myFile.setFilePath(path);
        int res = myFileMapper.insert(myFile);
        if (res > 0){
            logger.info(multipartFile.getOriginalFilename() + "文件上传成功");
            return DtoUtil.dto().success()
                    .addParam("path", path)
                    .addParam("fileName", fileName);
        }else{
            msg = "数据库插入失败";
            logger.error(msg);
            return DtoUtil.dto().failure()
                    .setMessage(msg);
        }
    }

    /**
     * 根据文件名称删除文件
     * @param fileName
     * @return
     */
    @Override
    @Transactional
    public boolean delFile(String fileName) {
        QueryWrapper<MyFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        queryWrapper.select("file_path");
        MyFile myFile = myFileMapper.selectOne(queryWrapper);
        boolean b = delFileByPath(myFile.getFilePath());
        int res = myFileMapper.delete(queryWrapper);
        if (b && res > 0){
            logger.info("文件["+ fileName + "]删除成功");
            return true;
        }else{
            StringBuilder msg = new StringBuilder();
            msg.append("文件["+ fileName + "]删除失败--");
            if (!b){
                msg.append("[本地文件删除失败]");
            }
            if (res <= 0){
                msg.append("[文件数据库删除失败]");
            }
            logger.error(msg.toString());
            return false;
        }
    }

    /**
     * 根据路径直接删除文件
     * @param path
     * @return
     */
    @Override
    public boolean delFileByPath(String path) {
        if(FileUtil.exist(path)){
            logger.error("文件不存在，删除失败");
            return false;
        }
        FileUtil.del(path);
        logger.info("[" + FileUtil.getName(path) + "]删除成功");
        return true;
    }
}
