package com.ShiXi.user.info.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.common.domin.dto.changeInfoDTO;
import com.ShiXi.user.info.studentInfo.domin.dto.StudentChangeInfoDTO;

import com.ShiXi.user.info.studentInfo.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/info")
@Api(tags = "用户信息")
public class infoController {

    @Resource
    private StudentInfoService studentInfoService;

    @PostMapping("/changeInfo")
    @ApiOperation("修改用户信息")
    public Result changeInfo(@RequestBody changeInfoDTO reqDTO) {
        //获取当前用户的身份
        String identification = reqDTO.getIdentification();
        if(identification.equals("student")){
            StudentChangeInfoDTO studentChangeInfoDTO=new StudentChangeInfoDTO();
            BeanUtils.copyProperties(reqDTO, studentChangeInfoDTO);
            studentInfoService.changeStudentInfo(studentChangeInfoDTO);
            return Result.ok();
        }
        else if(identification.equals("schoolFriend")){
            return Result.ok();
        }
        else if(identification.equals("teacher")){
            return Result.ok();
        }
        else if(identification.equals("enterprise")){
            return Result.ok();
        }
        return Result.fail("未知错误");
    }

    /**
     * 获取用户基本信息
     * @return 用户基本信息（昵称，头像等）
     */
    @GetMapping("/getInfo")
    @ApiOperation("获取用户全量信息")
    public Result getInfo(@RequestParam  String identification) {
        if(identification.equals("student")){
            Result studentInfo = studentInfoService.getStudentInfo();
            return Result.ok(studentInfo);
        }
        else if(identification.equals("schoolFriend")){
            return Result.ok();
        }
        else if(identification.equals("teacher")){
            return Result.ok();
        }
        else if(identification.equals("enterprise")){
            return Result.ok();
        }
        return Result.fail("未知错误");
    }
}
