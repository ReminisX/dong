package com.zijin.dong.component;

import com.zijin.dong.entity.base.ImageEntity;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class MinioComponent {

    private final Logger logger = LoggerFactory.getLogger(MinioComponent.class);

    private final MinioClient minioClient;

    @Autowired
    public MinioComponent(MinioClient minioClient) {
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
     * 获取对象的url链接，超时单位秒
     * @param bucket 桶名称
     * @param fileName 文件名
     * @return get方法的http链接
     */
    public String getObjectUrl(String bucket, String fileName, Integer time){
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
                            .expiry(time, TimeUnit.SECONDS)
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

    public boolean delObject(String bucketName, String objectName) {
        if (!existObject(bucketName, objectName)) {
            return false;
        }
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<ImageEntity> getAllUrlsInBucket(String bucketName, Integer maxKeys, Integer time) {
        List<ImageEntity> list = new ArrayList<>();
        if (!existBucket(bucketName)) {
            return list;
        }
        Iterable<Result<Item>> it = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .maxKeys(maxKeys)
                        .build()
        );
        // 遍历添加url
        it.forEach((result) -> {
            String objectName;
            try {
                objectName = result.get().objectName();
                String url = getObjectUrl(bucketName, objectName, time);
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setName(objectName);
                imageEntity.setUrl(url);
                list.add(imageEntity);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                throw new RuntimeException(e);
            }
        });
        return list;
    }

    /**
     * 重命名桶内元素
     * @param bucketName 桶名
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否更改成功
     */
    public boolean renameItem(String bucketName, String oldName, String newName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!existObject(bucketName, oldName)) {
            return false;
        }
        ObjectWriteResponse objectWriteResponse = minioClient.copyObject(CopyObjectArgs.builder()
                        .source(CopySource.builder()
                                .bucket(bucketName)
                                .object(oldName)
                                .build())
                        .bucket(bucketName)
                        .object(newName)
                        .build());
        delObject(bucketName, oldName);
        return true;
    }

}
