package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/student")
@Api(tags = "学生基本信息接口")
public class StudentInfoController {
    @Resource
    private StudentInfoService studentInfoService;

    /**
     * 保存学生基本信息
     * @param studentInfo 学生基本信息
     * @return 保存结果
     */
    @PostMapping("/register/basicInfo")
    @ApiOperation("保存学生基本信息")
    public Result saveStudentBasicInfo(@RequestBody StudentInfo studentInfo) {
        return studentInfoService.saveStudentBasicInfo(studentInfo);
    }

    /**
     * 获取用户基本信息
     * @return 用户基本信息（昵称，头像等）
     */
    @GetMapping("/me")
    @ApiOperation("获取用户基本信息（昵称，头像等）")
    public Result getStudentBasicInfo() {
        return studentInfoService.getStudentBasicInfo();
    }

    /**
     * 修改学生基本信息
     * @param studentInfo 待修改的学生基本信息
     * @return
     */
    @PostMapping("/changeInfo")
    @ApiOperation("修改学生基本信息")
    public Result changeStudentBasicInfo(@RequestBody StudentInfo studentInfo) {
        return studentInfoService.changeStudentBasicInfo(studentInfo);
    }
}
