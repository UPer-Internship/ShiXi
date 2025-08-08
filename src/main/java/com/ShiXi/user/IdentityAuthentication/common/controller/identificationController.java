package com.ShiXi.user.IdentityAuthentication.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification")
public class identificationController {
    @Resource
    IdentificationService identificationService;

    @PostMapping("/changeIdentification")
    @ApiOperation("切换身份")
    public Result changeIdentification(@RequestParam String identification){
        return identificationService.changeIdentification(identification);
    }

    @GetMapping("/getCurrentIdentification")
    @ApiOperation("获取当前身份 0：游客身份 1：学生 2：校友 3：老师 4：企业 5：团队")
    public Result getCurrentIdentification(){
        return identificationService.getCurrentIdentification();
    }


    @GetMapping("/getIdentificationStatus")
    @ApiOperation("获取用户的四种身份信息是否通过认证 0:未通过 1：已通过")
    public Result getIdentificationStatus(){
        Result identification = identificationService.getIdentificationStatus();
        return Result.ok(identification);
    }

    //TODO 设置相关管理权限 只有管理账号能看
    @GetMapping("/getIdentificationDataByUserId")
    @ApiOperation("通过用户id查看身份验证资料")
    public Result getIdentificationDataByUserId(@RequestParam Integer userId,@RequestParam String identification){
        return identificationService.getIdentificationDataByUserId(userId,identification);
    }
}
