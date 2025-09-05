package com.ShiXi.Resume.ResumePersonal.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.domin.dto.ResumePageQueryDTO;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


//    @PostMapping("/resume/basicInfo")
//    @ApiOperation("保存在线简历基本信息")
//    public Result saveResumeInfo(@RequestBody StudentInfo studentInfo) {
//        return onlineResumeService.saveResumeInfo(studentInfo);
//    }

//    /**
//     * 保存在线简历经历信息
//     * @param resumeExperience 经历信息（实习/工作/作品集）
//     * @return
//     */
//    @PostMapping("/resume/experience")
//    @ApiOperation("保存在线简历经历信息")
//    public Result saveExperienceInfo(@RequestBody ResumeExperience resumeExperience) {
//        return onlineResumeService.saveExperienceInfo(resumeExperience);
//    }

//    /**
//     * 修改在线简历基本信息
//     * @param studentInfo
//     * @return
//     */
//    @PostMapping("/resume/changeBasicInfo")
//    @ApiOperation("修改在线简历基本信息")
//    public Result changeResumeInfo(@RequestBody StudentInfo studentInfo) {
//        return onlineResumeService.changeResumeInfo(studentInfo);
//    }
//
//    /**
//     * 修改在线简历经历信息
//     * @param resumeExperience 经历信息（实习/工作/作品集）
//     * @return
//     */
//    @PostMapping("/resume/changeExperienceInfo")
//    @ApiOperation("修改在线简历经历信息")
//    public Result changeExperienceInfo(@RequestBody ResumeExperience resumeExperience) {
//        return onlineResumeService.changeExperienceInfo(resumeExperience);
//    }
//
//    /**
//     * 获取在线简历信息
//     * @return 在线简历的vo类
//     */
//    @GetMapping("/resume/myResume")
//    @ApiOperation("获取在线简历信息")
//    public Result getResumeInfo() {
//        return onlineResumeService.getOnlineResume();
//    }




}
