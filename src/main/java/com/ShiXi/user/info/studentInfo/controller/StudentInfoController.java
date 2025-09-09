package com.ShiXi.user.info.studentInfo.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.studentInfo.domin.dto.StudentChangeInfoDTO;
import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/info/student")
@Tag(name = "用户域通用服务")
public class StudentInfoController {

    @Resource
    StudentInfoService studentInfoService;
    @GetMapping("/getMyStudentInfo")
    public Result getMyStudentInfo(){
        return studentInfoService.getStudentInfo();
    }
    @PostMapping("/setMyStudentInfo")
    public Result setMyStudentInfo(@RequestBody StudentChangeInfoDTO studentChangeInfoDTO){
        return studentInfoService.setMyStudentInfo(studentChangeInfoDTO);
    }
}
