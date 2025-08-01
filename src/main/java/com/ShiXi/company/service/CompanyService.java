package com.ShiXi.company.service;

import com.ShiXi.company.entity.Company;
import com.ShiXi.common.domin.dto.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService extends IService<Company> {
    /**
     * 上传企业Logo并更新数据库
     * @param companyId 企业ID
     * @param file logo文件
     * @return 上传结果，包含logo URL
     */
    Result uploadLogo(Long companyId, MultipartFile file);

    /**
     * 根据企业ID获取企业Logo
     * @param companyId 企业ID
     * @return logo访问URL
     */
    Result getLogoByCompanyId(Long companyId);

    /**
     * 删除企业Logo，包括OSS文件和数据库字段
     * @param companyId 企业ID
     * @return 操作结果
     */
    Result deleteLogo(Long companyId);
} 