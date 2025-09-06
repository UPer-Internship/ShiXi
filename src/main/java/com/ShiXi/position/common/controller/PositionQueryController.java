package com.ShiXi.position.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.service.PositionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/position/query")
@Tag(name = "岗位查询接口")
public class PositionQueryController {
    
    @Resource
    private PositionQueryService positionQueryService;
    
    @GetMapping
    @Operation(summary = "根据ID和类型查询岗位，主要用来查看岗位收藏时获取岗位详情")
    public Result getJobByIdAndType(@RequestParam Long id, @RequestParam String type) {
        return positionQueryService.getJobByIdAndType(id, type);
    }
}