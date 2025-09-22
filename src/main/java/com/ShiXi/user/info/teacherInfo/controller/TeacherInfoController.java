package com.ShiXi.user.info.teacherInfo.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.teacherInfo.domin.dto.TeacherChangeInfoDTO;
import com.ShiXi.user.info.teacherInfo.service.TeacherInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/info/teacher")
@Tag(name = "教师信息")
public class TeacherInfoController {

    @Resource
    private TeacherInfoService teacherInfoService;

    @GetMapping("/getMyTeacherInfo")
    public Result getMyTeacherInfo(){
        return teacherInfoService.getTeacherInfo();
    }
    @PostMapping("/setMyTeacherInfo")
    public Result setMyTeacherInfo(@RequestBody TeacherChangeInfoDTO teacherChangeInfoDTO){
        return teacherInfoService.setMyTeacherInfo(teacherChangeInfoDTO);
    }
}
