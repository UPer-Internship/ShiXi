package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.dto.SchoolFriendUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo.SchoolFriendGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity.SchoolFriendIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface SchoolFriendIdentificationService extends IService<SchoolFriendIdentification> {
    Result uploadIdentificationPictureData(MultipartFile file);

    Result getMyIdentificationData();

    SchoolFriendGetIdentificationDataVO getIdentificationDataByUserId(Long userId);

    Result uploadIdentificationTextData(SchoolFriendUploadIdentificationTextDataReqDTO reqDTO);
}