package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.StudentBasicInfo;
import com.ShiXi.service.StudentBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentBasicInfoController {
    @Resource
    private StudentBasicInfoService studentBasicInfoService;

    @PostMapping("/register/basicInfo")
    public Result saveStudentBasicInfo(@RequestBody StudentBasicInfo studentBasicInfo){
        return studentBasicInfoService.saveStudentBasicInfo(studentBasicInfo);
    }
}
