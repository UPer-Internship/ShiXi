package com.ShiXi.common.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.OSSUtil;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.common.entity.User;
import com.ShiXi.user.common.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OSSUploadServiceImpl implements OSSUploadService {
    @Resource
    private OSSUtil ossUtil;

    @Resource
    private UserService userService;

    @Resource
    private com.aliyun.oss.OSS ossClient;

    @org.springframework.beans.factory.annotation.Value("${spring.oss.bucketName}")
    private String bucketName;

    // 头像存储目录
//    private static final String AVATAR_DIR = "avatar/";

    @Override
    public String uploadPicture(MultipartFile file,String url) {
        try {
            // 参数校验
            if (file == null || file.isEmpty()) {
                return "请选择要上传的文件";
            }

            // 检查文件类型
            String originalFilename = file.getOriginalFilename();
            if (!isValidImageFile(originalFilename)) {
                return "只支持jpg、jpeg、png、gif格式的图片文件";
            }

            // 检查文件大小（限制为5MB）
            if (file.getSize() > 5 * 1024 * 1024) {
                return "文件大小不能超过5MB";
            }

            // 上传到OSS
            String fileUrl = ossUtil.uploadAvatar(file, url);

            return fileUrl;

        } catch (IOException e) {
            log.error("上传失败", e);
            return "上传失败：" + e.getMessage();
        } catch (Exception e) {
            log.error("上传过程中发生异常", e);
            return "上传失败，请稍后重试";
        }
    }

    @Override
    public Result deletePicture(String url) {
        try {
            // 参数校验
            if (url == null || url.trim().isEmpty()) {
                return Result.fail("URL不能为空");
            }

            // 从URL中提取文件名
            String fileName = extractFileNameFromUrl(url);
            if (fileName == null) {
                return Result.fail("无效的URL");
            }

            // 删除OSS上的文件
            ossUtil.deleteFile(fileName);
            return Result.ok("删除成功");

        } catch (Exception e) {
            log.error("删除失败", e);
            return Result.fail("删除失败：" + e.getMessage());
        }
    }

    @Override
    public Result getPicture(String url) {
        return null;
    }

    @Override
    public Result getCurrentAvatar() {
        try {
            // 获取当前登录用户
            UserDTO currentUser = UserHolder.getUser();
            if (currentUser == null) {
                return Result.fail("用户未登录");
            }

            // 查询用户信息
            User user = userService.getById(currentUser.getId());
            if (user == null) {
                return Result.fail("用户信息不存在");
            }

            Map<String, Object> result = new HashMap<>();
            result.put("avatarUrl", user.getIcon());
            result.put("userId", user.getId());
            result.put("nickName", user.getNickName());

            return Result.ok(result);

        } catch (Exception e) {
            log.error("获取用户头像失败", e);
            return Result.fail("获取头像信息失败");
        }
    }

    @Override
    public Result updateUserAvatar(String avatarUrl) {
        try {
            // 获取当前登录用户
            UserDTO currentUser = UserHolder.getUser();
            if (currentUser == null) {
                return Result.fail("用户未登录");
            }

            // 更新用户头像信息
            User user = userService.getById(currentUser.getId());
            if (user == null) {
                return Result.fail("用户信息不存在");
            }

            user.setIcon(avatarUrl);
            userService.updateById(user);

            // 更新ThreadLocal中的用户信息
            currentUser.setIcon(avatarUrl);
            UserHolder.saveUser(currentUser);

            return Result.ok("头像信息更新成功");

        } catch (Exception e) {
            log.error("更新用户头像信息失败", e);
            return Result.fail("更新头像信息失败");
        }
    }

    /**
     * 检查是否为有效的图片文件
     */
    private boolean isValidImageFile(String filename) {
        if (filename == null) return false;
        String lowerFilename = filename.toLowerCase();
        return lowerFilename.endsWith(".jpg") ||
                lowerFilename.endsWith(".jpeg") ||
                lowerFilename.endsWith(".png") ||
                lowerFilename.endsWith(".gif");
    }

    /**
     * 从URL中提取OSS文件路径
     * OSS文件路径通常包含前缀，如 avatar/xxx.jpg
     */
    private String extractFileNameFromUrl(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
            return null;
        }

        try {
            // 移除URL协议和域名部分，只保留路径
            String filePath = avatarUrl;

            // 如果是完整的HTTP/HTTPS URL，提取路径部分
            if (avatarUrl.startsWith("http://") || avatarUrl.startsWith("https://")) {
                // 找到域名后的第一个斜杠
                int domainEndIndex = avatarUrl.indexOf("/", 8); // 跳过 http:// 或 https://
                if (domainEndIndex != -1) {
                    filePath = avatarUrl.substring(domainEndIndex + 1);
                }
            }

            // 如果路径以斜杠开头，去掉开头的斜杠
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }

            // 如果文件名包含查询参数，去掉查询参数
            if (filePath.contains("?")) {
                filePath = filePath.substring(0, filePath.indexOf("?"));
            }

            // 如果文件名包含锚点，去掉锚点
            if (filePath.contains("#")) {
                filePath = filePath.substring(0, filePath.indexOf("#"));
            }

            // 确保返回的是完整的OSS文件路径（包含前缀）
            return filePath.isEmpty() ? null : filePath;

        } catch (Exception e) {
            log.error("解析头像URL失败", e);
            return null;
        }
    }

    @Override
    public Result checkAvatarExists(String avatarUrl) {
        try {
            // 参数校验
            if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
                return Result.fail("头像URL不能为空");
            }

            // 从URL中提取文件名
            String fileName = extractFileNameFromUrl(avatarUrl);
            if (fileName == null) {
                return Result.fail("无效的头像URL");
            }

            // 检查文件是否存在
            boolean exists = ossClient.doesObjectExist(bucketName, fileName);

            Map<String, Object> result = new HashMap<>();
            result.put("exists", exists);
            result.put("fileName", fileName);

            return Result.ok(result);

        } catch (Exception e) {
            log.error("检查头像文件是否存在失败", e);
            return Result.fail("检查头像文件失败：" + e.getMessage());
        }
    }

    @Override
    public Result getAvatarPreviewUrl(String avatarUrl) {
        try {
            // 参数校验
            if (avatarUrl == null || avatarUrl.trim().isEmpty()) {
                return Result.fail("头像URL不能为空");
            }

            // 从URL中提取文件名
            String fileName = extractFileNameFromUrl(avatarUrl);
            if (fileName == null) {
                return Result.fail("无效的头像URL");
            }

            // 生成临时访问URL（1小时有效）
            Date expiration = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
            URL previewUrl = ossClient.generatePresignedUrl(bucketName, fileName, expiration);

            Map<String, Object> result = new HashMap<>();
            result.put("previewUrl", previewUrl != null ? previewUrl.toString() : null);
            result.put("expiration", expiration);

            return Result.ok(result);

        } catch (Exception e) {
            log.error("获取头像预览URL失败", e);
            return Result.fail("获取头像预览URL失败：" + e.getMessage());
        }
    }
}
