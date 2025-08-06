package com.ShiXi.Resume.ResumePersonal.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 在线简历相关接口
 */
@Slf4j
@RestController
@RequestMapping("/Resume")
@Api(tags = "在线简历相关接口")
public class ResumeController {
    @Resource
    private OnlineResumeService onlineResumeService;
    /**
     * 获取在线简历信息
     * @return 在线简历的vo类
     */
    @GetMapping("/getMyResume")
    @ApiOperation("获取我的简历")
    public Result getMyResume() {
        return onlineResumeService.getMyResume();
    }

    @PostMapping("/updateMyResume")
    @ApiOperation("修改我的简历")
    public Result updateMyExperience(@RequestBody UpdateResumeDTO reqDTO) {
        return onlineResumeService.updateMyExperience(reqDTO);
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
