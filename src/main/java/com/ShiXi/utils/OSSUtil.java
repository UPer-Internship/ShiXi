package com.ShiXi.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 阿里云OSS工具类，支持头像等文件的上传、删除、获取访问URL
 */
@Component
public class OSSUtil {
    @Resource
    private OSS ossClient;

    @Value("${spring.oss.bucketName}")
    private String bucketName;

    @Value("${spring.oss.endpoint}")
    private String endpoint;

    /**
     * 上传用户头像到OSS
     *
     * @param file 头像文件
     * @param dir  存储目录（如 avatar/）
     * @return 文件在OSS中的完整访问URL
     * @throws IOException IO异常
     */
    public String uploadAvatar(MultipartFile file, String dir) throws IOException {
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename != null && originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf('.')) : "";
        String fileName = dir + UUID.randomUUID() + suffix;
        // 上传到OSS
        ossClient.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream()));
        // 设置文件为公共读
        ossClient.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        // 返回访问URL
        return getFileUrl(fileName);
    }

    /**
     * 删除OSS上的文件
     *
     * @param fileName 文件名（带目录）
     */
    public void deleteFile(String fileName) {
        ossClient.deleteObject(bucketName, fileName);
    }

    /**
     * 获取文件的外链URL（默认1年有效）
     *
     * @param fileName 文件名（带目录）
     * @return 访问URL
     */
    public String getFileUrl(String fileName) {
        // 生成带签名的URL，有效期1年
        Date expiration = new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000);
        URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
        return url != null ? url.toString() : null;
    }
} 