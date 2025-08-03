//package com.ShiXi.user.info.studentInfo.controller;
//
//import com.ShiXi.common.domin.dto.Result;
//import com.ShiXi.user.info.studentInfo.domin.dto.studentBasicInfoReqDTO;
//import com.ShiXi.user.info.studentInfo.entity.StudentInfo;
//import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@RestController
//@RequestMapping("/student")
//@Api(tags = "学生基本信息接口")
//public class StudentInfoController {
//    @Resource
//    private StudentInfoService studentInfoService;
//
//    /**
//     * 保存学生基本信息
//     * @param reqDTO 学生基本信息
//     * @return 保存结果
//     */
//    @PostMapping("/register/basicInfo")
//    @ApiOperation("注册学生基本信息")
//    public Result registerStudentBasicInfo(@RequestBody studentBasicInfoReqDTO reqDTO) {
//        return studentInfoService.saveStudentBasicInfo(reqDTO);
//    }
//
//    /**
//     * 获取用户基本信息
//     * @return 用户基本信息（昵称，头像等）
//     */
//    @GetMapping("/me")
//    @ApiOperation("获取用户基本信息（昵称，头像等）")
//    public Result getStudentBasicInfo() {
//        return studentInfoService.getStudentBasicInfo();
//    }
//
//    /**
//     * 修改学生基本信息
//     * @param studentInfo 待修改的学生基本信息
//     * @return
//     */
//    @PostMapping("/changeInfo")
//    @ApiOperation("修改学生基本信息")
//    public Result changeStudentBasicInfo(@RequestBody StudentInfo studentInfo) {
//        return studentInfoService.changeStudentBasicInfo(studentInfo);
//    }
//}
