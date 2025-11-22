package com.ShiXi.position.common.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.service.PositionQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/position/query")
@Tag(name = "岗位查询相关接口")
public class PositionQueryController {
    
    @Resource
    private PositionQueryService positionQueryService;
    
    @GetMapping("/getJobByIdAndType")
    @Operation(summary = "根据id和type查询Job")
    public Result getJobByIdAndType(@RequestParam Long id, @RequestParam String type) {
        return positionQueryService.getJobByIdAndType(id, type);
    }
    
    /**
     * 分类分页查询我发布的岗位
     * @param page 页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @param type 岗位类型：正职、兼职、实习（必传，不传默认为正职）
     * @param status 岗位状态：0-不可见，1-可见（可选，不传则查询所有状态）
     * @return 分页查询结果
     */
    @GetMapping("/myPublishedJobs")
    @Operation(summary = "分类分页查询我发布的岗位")
    public Result pageQueryMyPublishedJobs(
            @Parameter(description = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @Parameter(description = "岗位类型：正职、兼职、实习（不传默认为兼职）") @RequestParam(value = "type", defaultValue = "兼职") String type,
            @Parameter(description = "岗位状态：0-不可见，1-可见 不填-全部（可选）") @RequestParam(value = "status", required = false) Integer status) {
        return positionQueryService.pageQueryMyPublishedJobs(page, pageSize, type, status);
    }
    
    /**
     * 分类分页模糊搜索岗位推荐
     * @param keyword 搜索关键词
     * @param page 页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @param type 岗位类型：正职、兼职、实习（可选，为空则搜索所有类型）
     * @param province 省份筛选（可选）
     * @param city 城市筛选（可选）
     * @param category 职位分类筛选（可选）
     * @param salaryMin 最低薪资筛选（可选）
     * @param salaryMax 最高薪资筛选（可选）
     * @param educationRequirement 学历要求筛选（可选）
     * @param industry 行业筛选（可选）
     * @param enterpriseScale 公司规模筛选（可选）
     * @return 搜索结果
     */
    @GetMapping("/searchJobs")
    @Operation(summary = "分类分页模糊搜索岗位推荐")
    public Result searchJobs(
            @Parameter(description = "搜索关键词") @RequestParam(value = "keyword", required = false) String keyword,
            @Parameter(description = "页码") @RequestParam(value = "page", defaultValue = "1") Integer page,
            @Parameter(description = "每页大小") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @Parameter(description = "岗位类型：正职、兼职、实习（可选）") @RequestParam(value = "type", required = false) String type,
            @Parameter(description = "省份筛选") @RequestParam(value = "province", required = false) String province,
            @Parameter(description = "城市筛选") @RequestParam(value = "city", required = false) String city,
            @Parameter(description = "职位分类筛选") @RequestParam(value = "category", required = false) String category,
            @Parameter(description = "最低薪资，月薪，单位：元/月") @RequestParam(value = "salaryMin", required = false) Double salaryMin,
            @Parameter(description = "最高薪资，月薪，单位：元/月") @RequestParam(value = "salaryMax", required = false) Double salaryMax,
            @Parameter(description = "学历要求筛选") @RequestParam(value = "educationRequirement", required = false) String educationRequirement,
            @Parameter(description = "行业筛选") @RequestParam(value = "industry", required = false) String industry,
            @Parameter(description = "公司规模筛选") @RequestParam(value = "enterpriseScale", required = false) String enterpriseScale) {
        return positionQueryService.searchJobs(keyword, page, pageSize, type, province, city, category, salaryMin, salaryMax, educationRequirement, industry, enterpriseScale);
    }

    /**
     * 查询我投递给某个人的所有岗位信息
     * @param publisherId 岗位发布者ID
     * @return 岗位信息列表
     */
    @GetMapping("/myApplicationsToPublisher")
    @Operation(summary = "查询我投递给某个人的所有岗位信息")
    public Result getJobsAppliedToPublisher(
            @Parameter(description = "岗位发布者ID") @RequestParam Long publisherId) {
        return positionQueryService.getJobsAppliedToPublisher(publisherId);
    }
}