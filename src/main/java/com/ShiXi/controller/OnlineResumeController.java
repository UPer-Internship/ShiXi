package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ResumeExperience;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.service.OnlineResumeService;
import com.ShiXi.utils.UserHolder;
import com.ShiXi.vo.OnlineResumeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 在线简历相关接口
 */
@Slf4j
@RestController
@RequestMapping("/student")
public class OnlineResumeController {
    @Resource
    private OnlineResumeService onlineResumeService;

    @PostMapping("/resume/basicInfo")
    public Result saveResumeInfo(@RequestBody StudentInfo studentInfo) {
        return onlineResumeService.saveResumeInfo(studentInfo);
    }

    @PostMapping("/resume/experience")
    public Result saveExperienceInfo(@RequestBody ResumeExperience resumeExperience) {
        return onlineResumeService.saveExperienceInfo(resumeExperience);
    }

    @PostMapping("/resume/changeBasicInfo")
    public Result changeResumeInfo(@RequestBody StudentInfo studentInfo) {
        return onlineResumeService.changeResumeInfo(studentInfo);
    }

    @PostMapping("/resume/changeExperienceInfo")
    public Result changeExperienceInfo(@RequestBody ResumeExperience resumeExperience) {
        return onlineResumeService.changeExperienceInfo(resumeExperience);
    }

    @GetMapping("/resume/myResume")
    public Result getResumeInfo() {
        return onlineResumeService.getOnlineResume();
    }


}
