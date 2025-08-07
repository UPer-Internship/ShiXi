package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity.SchoolFriendIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface SchoolFriendIdentificationService extends IService<SchoolFriendIdentification> {
    Result uploadIdentificationData(String type, MultipartFile file);

    Result getMyIdentification(String type);

    Result getIdentificationDataByUserId(Integer userId, String identification, String type);


}