package com.ShiXi.position.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.service.PositionCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/position/category")
@Tag(name = "岗位类别相关接口")
public class PositionCategoryController {

    @Resource
    private PositionCategoryService positionCategoryService;

    @GetMapping("/getJobCategoryList")
    @Operation(summary = "获取岗位类别列表")
    public Result getJobCategoryList() {
        return positionCategoryService.getJobCategoryList();
    }
}
