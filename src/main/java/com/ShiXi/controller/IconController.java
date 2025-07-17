package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.service.IconService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "头像相关接口")
public class IconController {

    @Resource
    private IconService iconService;

    /**
     * 上传用户头像
     * @param file 头像文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    @ApiOperation("上传用户头像")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) {
        return iconService.uploadAvatar(file);
    }

    /**
     * 删除用户头像
     * @param avatarUrl 头像URL
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @ApiOperation("删除用户头像")
    public Result deleteAvatar(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.deleteAvatar(avatarUrl);
    }

    /**
     * 获取用户当前头像
     * @return 头像信息
     */
    @GetMapping("/current")
    @ApiOperation("获取用户当前头像")
    public Result getCurrentAvatar() {
        return iconService.getCurrentAvatar();
    }

    /**
     * 更新用户头像信息
     * @param avatarUrl 头像URL
     * @return 更新结果
     */
    @PutMapping("/update")
    @ApiOperation("更新用户头像信息")
    public Result updateUserAvatar(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.updateUserAvatar(avatarUrl);
    }
    
    /**
     * 检查头像文件是否存在
     * @param avatarUrl 头像URL
     * @return 检查结果
     */
    @GetMapping("/check")
    @ApiOperation("检查头像文件是否存在")
    public Result checkAvatarExists(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.checkAvatarExists(avatarUrl);
    }
    
    /**
     * 获取头像预览URL
     * @param avatarUrl 头像URL
     * @return 预览URL
     */
    @GetMapping("/preview")
    @ApiOperation("获取头像预览URL")
    public Result getAvatarPreviewUrl(@RequestParam("avatarUrl") String avatarUrl) {
        return iconService.getAvatarPreviewUrl(avatarUrl);
    }
}
