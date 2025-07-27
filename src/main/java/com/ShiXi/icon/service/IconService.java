package com.ShiXi.icon.service;

import com.ShiXi.common.domin.dto.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * 头像相关服务接口
 */
public interface IconService {
    
    /**
     * 上传用户头像
     * @param file 头像文件
     * @return 上传结果，包含头像URL
     */
    Result uploadAvatar(MultipartFile file);
    
    /**
     * 删除用户头像
     * @param avatarUrl 头像URL
     * @return 删除结果
     */
    Result deleteAvatar(String avatarUrl);
    
    /**
     * 获取用户当前头像
     * @return 头像信息
     */
    Result getCurrentAvatar();
    
    /**
     * 更新用户头像信息到数据库
     * @param avatarUrl 头像URL
     * @return 更新结果
     */
    Result updateUserAvatar(String avatarUrl);
    
    /**
     * 检查头像文件是否存在
     * @param avatarUrl 头像URL
     * @return 检查结果
     */
    Result checkAvatarExists(String avatarUrl);
    
    /**
     * 获取头像预览URL（临时访问链接）
     * @param avatarUrl 头像URL
     * @return 预览URL
     */
    Result getAvatarPreviewUrl(String avatarUrl);
} 