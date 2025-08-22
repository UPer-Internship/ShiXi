package com.ShiXi.user.IdentityAuthentication.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IdentificationService extends IService<Identification> {
    Result getIdentificationStatus();

//    Result getMyIdentification(String identification, String type);

//    Result getIdentificationDataByUserId(Integer userId, String identification);

    Result changeIdentification(String identification);

    Integer getCurrentIdentification();

    Result getIdentificationDataRequest();

    void notifyAdminToAudit(Integer identification);

    Result passIdentificationDataRequest(Long userId, String identification);

    Result refuseIdentificationDataRequest(Long userId, String identification);
}
