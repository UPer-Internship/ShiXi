package com.ShiXi.user.IdentityAuthentication.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.common.service.impl.IdentificationServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/identification")
public class identificationController {
    @Resource
    IdentificationService identificationService;
    @Autowired
    private IdentificationServiceImpl identificationServiceImpl;

    //    @PostMapping("/notifyAdminToAudit")
//    @ApiOperation("用户上传的认证资料后通知审核端端口")
//    public Result notifyAdminToAudit(@RequestParam String identification){
//        return identificationService.notifyAdminToAudit(identification);
//    }
    @PostMapping("/changeIdentification")
    @ApiOperation("切换身份")
    public Result changeIdentification(@RequestParam Integer identification){
        return identificationService.changeIdentification(identification);
    }
    @GetMapping("/getCurrentIdentification")
    @ApiOperation("获取当前身份 0：游客身份 1：学生 2：校友 3：老师 4：企业 5：学生团队 6：管理员")
    public Result getCurrentIdentification(){

        return Result.ok(identificationService.getCurrentIdentification());
    }
    @GetMapping("/getAllIdentificationStatus")
    @ApiOperation("获取用户的四种身份信息是否通过认证 0:未提交过资料 1：已提交待审核 2：审核不通过 3：审核通过，拥有此身份")
    public Result getAllIdentificationStatus(){
        return identificationService.getAllIdentificationStatus();

    }
    @GetMapping("/getSpecifiedIdentificationStatus")
    @ApiOperation("获取用户某身份信息是否通过认证 0:未提交过资料 1：已提交待审核 2：审核不通过 3：审核通过，拥有此身份")
    public Result getSpecifiedIdentificationStatus(Integer identification){
        return  identificationService.getSpecifiedIdentificationStatus(identification);

    }
    @GetMapping("/getRefusedReason")
    @ApiOperation("获取被拒绝的理由")
    public Result getRefusedReason(Integer identification){
        return  identificationService.getRefusedReason(identification);

    }

    //TODO 设置相关管理权限 只有管理账号能看
    @GetMapping("/admin/getIdentificationDataRequest")
    @ApiOperation("管理端获取审核身份验证资料")
    public Result getIdentificationDataRequest(){
        return identificationService.getIdentificationDataRequest();
    }
    @GetMapping("/admin/getIdentificationDataByUserId")
    @ApiOperation("管理端获取审核身份验证资料")
    public Result getIdentificationDataByUserId(Integer userId, Integer identification){
        return identificationService.getIdentificationDataByUserId(userId, identification);
    }
    @PostMapping("/admin/passIdentificationDataRequest")
    @ApiOperation("管理端通过审核身份验证资料")
    public Result passIdentificationDataRequest(){
        return identificationService.passIdentificationDataRequest();
    }
    @PostMapping("/admin/refuseIdentificationDataRequest")
    @ApiOperation("管理端不通过审核身份验证资料")
    public Result refuseIdentificationDataRequest(String  reason){
        return identificationService.refuseIdentificationDataRequest(reason);
    }

}
