package com.ShiXi.user.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.domin.dto.ChangeInfoDTO;
import com.ShiXi.user.common.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息服务")
public class UserController {
    @Resource
    private UserService userService;


    @PostMapping("/changeMyUserInfo")
    public Result changeMyUserInfo(@RequestBody ChangeInfoDTO changeInfoDTO){
        return userService.changeMyUserInfo(changeInfoDTO);
    }
    @PostMapping("/changeMyIcon")
    public Result changeMyIcon(@RequestParam MultipartFile file){
        return userService.changeMyIcon(file);
    }
    
    @GetMapping("/getUserInfoById")
    public Result getUserInfoById(@RequestParam Long id){
        return userService.getUserInfoById(id);
    }

    @GetMapping("/getUserInfoByUuid")
    public Result getUserInfoByUuid(@RequestParam String uuid){
        return userService.getUserInfoByUuid(uuid);
    }

    @GetMapping("/getMyUserInfo")
    public Result getMyUserInfo(){
        return userService.getMyUserInfo();
    }

//    @GetMapping("/isLogin")
//    public Result isLogin(@RequestParam String token){
//
//    }
}
