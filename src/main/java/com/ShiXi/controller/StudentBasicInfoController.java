package com.ShiXi.controller;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.StudentBasicInfo;
import com.ShiXi.service.StudentBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/me")
    public Result getStudentBasicInfo(){
        return studentBasicInfoService.getStudentBasicInfo();
    }
}
