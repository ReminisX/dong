package com.zijin.dong.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class MinioUtil {

    private final Logger logger = LoggerFactory.getLogger(MinioUtil.class);

    private final MinioClient minioClient;

    @Autowired
    public MinioUtil(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * minio文件上传
     * @return 是否上传成功
     */
    @SneakyThrows
    public boolean upload(String bucket, String fileName, InputStream inputStream) {
        MakeBucketArgs makeBucketArgs = MakeBucketArgs
                .builder()
                .bucket(bucket)
                .build();
        try {
            if (!existBucket(bucket)) {
                minioClient.makeBucket(makeBucketArgs);
                logger.warn("无对应桶，已创建[" + bucket + "]桶");
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | InvalidKeyException | XmlParserException e) {
            logger.error("桶创建失败");
            return false;
        }
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .stream(inputStream, inputStream.available(), -1)
                            .build()
            );
        } catch (ErrorResponseException | InsufficientDataException | InternalException | XmlParserException | NoSuchAlgorithmException | ServerException | InvalidResponseException | InvalidKeyException | IOException e) {
            logger.error("文件上传异常");
            return false;
        } finally {
            inputStream.close();
        }
        return true;
    }

    /**
     * 获取对象的url链接
     * @param bucket 桶名称
     * @param fileName 文件名
     * @return get方法的http链接
     */
    public String getObjectUrl(String bucket, String fileName) {
        // 桶及对象是否存在判断
        if (!existObject(bucket, fileName)) {
            return null;
        }
        // 获取对象url
        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(fileName)
                            .expiry(2, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            logger.error("获取对象【" + fileName +"】url失败");
        }
        return url;
    }

    /**
     * 获取对象输入流
     * @param bucket 桶名称
     * @param fileName 文件名称
     * @return 字节流
     */
    public InputStream getObject(String bucket, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build()
            );
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException | NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            logger.error("获取桶【" + bucket + "】内对象【"  + fileName + "】失败");
            return null;
        }
        return inputStream;
    }

    /**
     * 桶是否存在判断
     * @param bucket 桶名称
     * @return 是否存在
     */
    public boolean existBucket(String bucket) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs
                .builder()
                .bucket(bucket)
                .build();
        try {
            if (!minioClient.bucketExists(bucketExistsArgs)) {
                logger.warn("无对应桶，");
                return false;
            }
        } catch (Exception e) {
            logger.error("桶检查异常");
            return false;
        }
        return true;
    }

    /**
     * 首先判断桶是否存在，之后再判断对象是否存在
     * @param bucket 桶名称
     * @param fileName 文件名称
     * @return 对象是否存在
     */
    public boolean existObject(String bucket, String fileName) {
        if (!existBucket(bucket)) {
            return false;
        }
        if (Objects.isNull(getObject(bucket, fileName))) {
            return false;
        }
        return true;
    }

}
