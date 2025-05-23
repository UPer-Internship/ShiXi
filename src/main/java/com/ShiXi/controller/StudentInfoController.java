package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.service.StudentInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentInfoController {
    @Resource
    private StudentInfoService studentInfoService;

    @PostMapping("/register/basicInfo")
    public Result saveStudentBasicInfo(@RequestBody StudentInfo studentInfo) {
        return studentInfoService.saveStudentBasicInfo(studentInfo);
    }

    @GetMapping("/me")
    public Result getStudentBasicInfo() {
        return studentInfoService.getStudentBasicInfo();
    }

    @PostMapping("/changeInfo")
    public Result changeStudentBasicInfo(@RequestBody StudentInfo studentInfo) {
        return studentInfoService.changeStudentBasicInfo(studentInfo);
    }
}
