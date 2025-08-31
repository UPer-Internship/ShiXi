package com.ShiXi.user.IdentityAuthentication.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IdentificationService extends IService<Identification> {
    Result getAllIdentificationStatus();

//    Result getMyIdentification(String identification, String type);

//    Result getIdentificationDataByUserId(Integer userId, String identification);

    Result changeIdentification(Integer identification);

    Integer getCurrentIdentification();

    Result getIdentificationDataRequest();

    void notifyAdminToAudit(Integer identification);

    Result passIdentificationDataRequest();

    Result refuseIdentificationDataRequest(String reason);

    Result getSpecifiedIdentificationStatus(Integer identification);


    Result getRefusedReason(Integer identification);
}
