package com.ShiXi.user.废弃info.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.common.service.UserService;
import com.ShiXi.user.common.domin.dto.ChangeInfoDTO;

import com.ShiXi.user.废弃info.studentInfo.service.StudentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/info")
@Api(tags = "用户信息")
public class infoController {

    @Resource
    private StudentInfoService studentInfoService;
    @Resource UserService userService;


    @PostMapping("/changeInfo")
    @ApiOperation("修改用户信息")
    public Result changeInfo(@RequestBody ChangeInfoDTO reqDTO) {
        //获取当前用户的身份
        Integer identification = UserHolder.getUser().getIdentification();

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
