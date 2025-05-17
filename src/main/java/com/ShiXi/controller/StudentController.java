//package com.ShiXi.controller;
//
//import com.ShiXi.dto.Result;
//import com.ShiXi.entity.Student;
//import com.ShiXi.service.StudentService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@Slf4j
//@RestController
//@RequestMapping("/student")
//public class StudentController {
//    @Resource
//    private StudentService studentService;
//    //使用密码登录已经弃用
//    @PostMapping("/login/byAccount")
//    public Result loginByAccount(@RequestParam("account")String account, @RequestParam("password")String password) {
//        return studentService.loginByAccount(account,password);
//    }
//
//    @PostMapping("/login/sendCode")
//    public Result sendCode(@RequestParam("phone")String phone) {
//        return studentService.sendCode(phone);
//    }
//
//    @PostMapping("/login/byPhone")
//    public Result loginByPhone(@RequestParam("phone")String phone,@RequestParam("code")String code) {
//        return studentService.loginByPhone(phone,code);
//    }
//
//
//}
