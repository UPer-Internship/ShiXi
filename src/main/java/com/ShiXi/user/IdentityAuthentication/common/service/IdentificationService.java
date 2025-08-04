package com.ShiXi.user.IdentityAuthentication.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface IdentificationService extends IService<Identification> {
    Result getIdentificationStatus();

    Result toIdentification(String identification, String type, MultipartFile file);

    Result getMyIdentification(String identification, String type);

    Result getIdentificationDataByUserId(Integer userId, String identification, String type);
}
