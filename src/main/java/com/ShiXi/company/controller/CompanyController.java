package com.ShiXi.company.controller;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.company.entity.Company;
import com.ShiXi.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/company")
@Tag(name = "企业相关接口")
public class CompanyController {
    @Resource
    private CompanyService companyService;

    /**
     * 新增企业
     * @param company 企业对象
     * @return 操作结果
     */
    @PostMapping("/add")
    @Operation(summary = "新增企业")
    public Result add(@RequestBody Company company) {
        boolean saved = companyService.save(company);
        return saved ? Result.ok() : Result.fail("新增失败");
    }

    /**
     * 更新企业
     * @param company 企业对象
     * @return 操作结果
     */
    @PostMapping("/update")
    @Operation(summary = "更新企业")
    public Result update(@RequestBody Company company) {
        boolean updated = companyService.updateById(company);
        return updated ? Result.ok() : Result.fail("更新失败");
    }

    /**
     * 删除企业
     * @param id 企业ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    @Operation(summary = "删除企业")
    public Result delete(@RequestParam Long id) {
        boolean removed = companyService.removeById(id);
        return removed ? Result.ok() : Result.fail("删除失败");
    }

    /**
     * 根据ID查询企业
     * @param id 企业ID
     * @return 企业对象或失败信息
     */
    @GetMapping("/queryById")
    @Operation(summary = "根据ID查询企业")
    public Result queryById(@RequestParam Long id) {
        Company company = companyService.getById(id);
        return company != null ? Result.ok(company) : Result.fail("未找到企业");
    }

    /**
     * 查询所有企业列表
     * @return 企业列表
     */
    @GetMapping("/list")
    @Operation(summary = "企业列表")
    public Result list() {
        return Result.ok(companyService.list());
    }

    /**
     * 上传企业Logo并更新数据库
     * @param companyId 企业ID
     * @param file logo文件
     * @return 上传结果及logo访问URL
     */
    @PostMapping("/uploadLogo")
    @Operation(summary = "上传企业Logo")
    public Result uploadLogo(@RequestParam Long companyId, @RequestParam("file") MultipartFile file) {
        return companyService.uploadLogo(companyId, file);
    }

    /**
     * 根据企业ID获取企业Logo
     * @param companyId 企业ID
     * @return logo访问URL
     */
    @GetMapping("/logo")
    @Operation(summary = "根据企业ID获取企业Logo")
    public Result getLogoByCompanyId(@RequestParam Long companyId) {
        return companyService.getLogoByCompanyId(companyId);
    }

    /**
     * 删除企业Logo，包括OSS文件和数据库字段
     * @param companyId 企业ID
     * @return 操作结果
     */
    @DeleteMapping("/logo")
    @Operation(summary = "删除企业Logo")
    public Result deleteLogo(@RequestParam Long companyId) {
        return companyService.deleteLogo(companyId);
    }
} 