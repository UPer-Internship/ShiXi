package com.ShiXi.icon.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.icon.service.IconService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 用来提供与用户头像的上传等相关的服务
 */
@Slf4j
@RestController
@RequestMapping("/icon")
@Tag(name = "头像相关接口")
public class IconController {

    @Resource
    private IconService iconService;

    /**
     * 上传用户头像
     * @param file 头像文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    @Operation(summary = "上传用户头像")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) {
        return iconService.uploadAvatar(file);
    }

    /**
     * 删除用户头像
     * @param avatarUrl 头像URL
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除用户头像")
    public Result deleteAvatar(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.deleteAvatar(avatarUrl);
    }

    /**
     * 获取用户当前头像
     * @return 头像信息
     */
    @GetMapping("/current")
    @Operation(summary = "获取用户当前头像")
    public Result getCurrentAvatar() {
        return iconService.getCurrentAvatar();
    }

    /**
     * 更新用户头像信息
     * @param avatarUrl 头像URL
     * @return 更新结果
     */
    @PutMapping("/update")
    @Operation(summary = "更新用户头像信息")
    public Result updateUserAvatar(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.updateUserAvatar(avatarUrl);
    }
    
    /**
     * 检查头像文件是否存在
     * @param avatarUrl 头像URL
     * @return 检查结果
     */
    @GetMapping("/check")
    @Operation(summary = "检查头像文件是否存在")
    public Result checkAvatarExists(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.checkAvatarExists(avatarUrl);
    }
    
    /**
     * 获取头像预览URL
     * @param avatarUrl 头像URL
     * @return 预览URL
     */
    @GetMapping("/preview")
    @Operation(summary = "获取头像预览URL")
    public Result getAvatarPreviewUrl(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.getAvatarPreviewUrl(avatarUrl);
    }
}
