package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.dto.EnterpriseUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface EnterpriseIdentificationService extends IService<EnterpriseIdentification> {
    Result getIdentificationDataByUserId(Integer userId);

    Result uploadIdentificationPictureData(String type, MultipartFile file);

    Result getMyIdentificationData();

    Result uploadIdentificationTextData(EnterpriseUploadIdentificationTextDataReqDTO reqDTO);
}
