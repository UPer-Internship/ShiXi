package com.ShiXi.user.info.enterpriseInfo.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.enterpriseInfo.domin.dto.EnterpriseChangeInfoDTO;
import com.ShiXi.user.info.enterpriseInfo.service.EnterpriseInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user/info/enterprise")
@Tag(name = "企业信息")
public class EnterpriseInfoController {
    @Resource
    private EnterpriseInfoService enterpriseInfoService;

    @GetMapping("/getMyEnterpriseInfo")
    public Result getMyEnterpriseInfo(){
        return enterpriseInfoService.getEnterpriseInfo();
    }

    @PostMapping("/setMyEnterpriseInfo")
    public Result setMyEnterpriseInfo(@RequestBody EnterpriseChangeInfoDTO enterpriseChangeInfoDTO){
        return enterpriseInfoService.setMyEnterpriseInfo(enterpriseChangeInfoDTO);
    }


}
