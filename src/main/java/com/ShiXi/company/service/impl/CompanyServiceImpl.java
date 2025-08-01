package com.ShiXi.company.service.impl;

import com.ShiXi.company.entity.Company;
import com.ShiXi.common.mapper.CompanyMapper;
import com.ShiXi.company.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.utils.OSSUtil;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private OSSUtil ossUtil;

    /**
     * 企业logo在OSS中的目录前缀
     */
    private static final String LOGO_OSS_DIR = "company/logo/";

    @Override
    public Result uploadLogo(Long companyId, MultipartFile file) {
        try {
            String url = ossUtil.uploadAvatar(file, LOGO_OSS_DIR);
            Company company = this.getById(companyId);
            if (company == null) {
                return Result.fail("企业不存在");
            }
            company.setLogo(url);
            boolean updated = this.updateById(company);
            if (!updated) {
                return Result.fail("数据库更新失败");
            }
            return Result.ok(url);
        } catch (Exception e) {
            return Result.fail("上传失败: " + e.getMessage());
        }
    }

    @Override
    public Result getLogoByCompanyId(Long companyId) {
        Company company = this.getById(companyId);
        if (company == null) {
            return Result.fail("企业不存在");
        }
        String logoUrl = company.getLogo();
        return Result.ok(logoUrl);
    }

    @Override
    public Result deleteLogo(Long companyId) {
        Company company = this.getById(companyId);
        if (company == null) {
            return Result.fail("企业不存在");
        }
        String logoUrl = company.getLogo();
        if (logoUrl == null || logoUrl.isEmpty()) {
            return Result.fail("企业未设置Logo");
        }
        // 提取OSS文件名
        String fileName = logoUrl.substring(logoUrl.indexOf(LOGO_OSS_DIR));
        try {
            ossUtil.deleteFile(fileName);
            company.setLogo("");
            boolean updated = this.updateById(company);
            if (!updated) {
                return Result.fail("数据库更新失败");
            }
            return Result.ok();
        } catch (Exception e) {
            return Result.fail("删除失败: " + e.getMessage());
        }
    }
} 