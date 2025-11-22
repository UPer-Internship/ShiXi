package com.ShiXi.Resume.ResumePersonal.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.domin.dto.ResumePageQueryDTO;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.ShiXi.user.common.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 在线简历相关接口
 */
@Slf4j
@RestController
@RequestMapping("/Resume")
@Tag(name = "在线简历相关接口")
public class ResumeController {
    @Resource
    private OnlineResumeService onlineResumeService;
    
    @Resource
    private UserService userService;
    
    /**
     * 获取在线简历信息
     * @return 在线简历的vo类
     */
    @GetMapping("/getMyResume")
    @Operation(summary = "获取我的简历")
    public Result getMyResume() {
        return onlineResumeService.getMyResume();
    }

    @PostMapping("/updateMyResume")
    @Operation(summary = "修改我的简历")
    public Result updateMyExperience(@RequestBody UpdateResumeDTO reqDTO) {
        return onlineResumeService.updateMyExperience(reqDTO);
    }

    /**
     * 分页查询简历公开信息
     * @param page 页码
     * @param pageSize 每页记录数
     * @param expectedPosition 期望职位，用于模糊查询
     * @return 分页查询结果
     */
    @GetMapping("/pageQueryPublic")
    @Operation(summary = "分页查询简历公开信息")
    public Result pageQueryPublicResumes(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String expectedPosition) {
        // 处理空字符串为null
        expectedPosition = (expectedPosition != null && expectedPosition.trim().isEmpty()) ? null : expectedPosition;
        
        ResumePageQueryDTO queryDTO = new ResumePageQueryDTO(page, pageSize, expectedPosition);
        return onlineResumeService.pageQueryPublicResumes(queryDTO);
    }

    /**
     * 根据简历ID查询简历详情（包含文字资料和附件OSS URL）
     * @param resumeId 简历ID
     * @return 简历详情
     */
    @GetMapping("/getResumeById")
    @Operation(summary = "根据简历ID查询简历详情")
    public Result getResumeById(@RequestParam Long resumeId) {
        return onlineResumeService.getResumeByResumeId(resumeId);
    }

    /**
     * 根据用户ID查询简历（包含文字资料和附件OSS URL）
     * @param userId 用户ID
     * @return 简历信息
     */
    @GetMapping("/getResumeByUserId")
    @Operation(summary = "根据用户ID查询简历")
    public Result getResumeByUserId(@RequestParam Long userId) {
        return onlineResumeService.getResumeByUserId(userId);
    }

    /**
     * 根据用户UUID查询简历（包含文字资料和附件OSS URL）
     * @param uuid 用户UUID
     * @return 简历信息
     */
    @GetMapping("/getResumeByUuid")
    @Operation(summary = "根据用户UUID查询简历")
    public Result getResumeByUuid(@RequestParam String uuid) {
        return onlineResumeService.getResumeByUuid(uuid);
    }





    /**
     * 上传简历附件到OSS
     * @param file 简历附件文件
     * @return 上传结果，包含OSS URL
     */
    @PostMapping("/uploadAttachment/v2")
    @Operation(summary = "(新)上传简历附件")
    public Result uploadResumeAttachmentV2(@RequestParam("file") MultipartFile file,@RequestParam("attachmentName")String attachmentName) {
        return onlineResumeService.uploadResumeAttachmentV2(file,attachmentName);
    }
    /**
     * 删除简历附件
     * @param attachmentId 简历附件文件
     * @return 成功状态
     */
    @PostMapping("/deleteAttachment")
    @Operation(summary = "根据简历附件id删除简历附件")
    public Result deleteAttachment(@RequestParam Long attachmentId) {
        return onlineResumeService.deleteAttachment(attachmentId);
    }

    /**
     * 查看简历附件列表
     * @return 简历附件id列表
     */
    @PostMapping("/getAttachmentIds")
    @Operation(summary = "查看简历附件的列表集合，返回简历附件的id集合")
    public Result getAttachmentIds() {
        return onlineResumeService.getAttachmentIds();
    }

    /**
     * 查看简历附件列表
     * @return 简历附件id列表
     */
    @PostMapping("/getAttachmentById")
    @Operation(summary = "根据简历附件id获取简历附件url")
    public Result getAttachmentById(Long attachmentId) {
        return onlineResumeService.getAttachmentById(attachmentId);
    }























    /**
     * 上传简历附件到OSS
     * @param file 简历附件文件
     * @return 上传结果，包含OSS URL
     */
    @PostMapping("/uploadAttachment")
    @Operation(summary = "(废弃)上传简历附件")
    public Result uploadResumeAttachment(@RequestParam("file") MultipartFile file) {
        return onlineResumeService.uploadResumeAttachment(file);
    }

    /**
     * 查看简历附件OSS URL
     * @param resumeId 简历ID
     * @return 附件OSS URL
     */
    @GetMapping("/getAttachmentUrl")
    @Operation(summary = "（废弃）根据简历id获取简历附件URL")
    public Result getResumeAttachmentUrl(@RequestParam Long resumeId) {
        return onlineResumeService.getResumeAttachmentUrl(resumeId);
    }







}
