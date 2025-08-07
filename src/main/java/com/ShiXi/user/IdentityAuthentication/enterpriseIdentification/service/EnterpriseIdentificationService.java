package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface EnterpriseIdentificationService extends IService<EnterpriseIdentification> {
    Result toIdentification(String type, MultipartFile file);

    Result getMyIdentification(String identification, String type);

    Result getIdentificationDataByUserId(Integer userId, String identification, String type);

    Result changeIdentification(String identification);
}
