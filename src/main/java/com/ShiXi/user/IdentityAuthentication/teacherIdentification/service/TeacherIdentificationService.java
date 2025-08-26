package com.ShiXi.user.IdentityAuthentication.teacherIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.dto.TeacherUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo.TeacherGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity.TeacherIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherIdentificationService extends IService<TeacherIdentification> {
    Result uploadIdentificationPictureData(MultipartFile file);

    Result getMyIdentificationData();

    TeacherGetIdentificationDataVO getIdentificationDataByUserId(Long userId);

    Result uploadIdentificationTextData(TeacherUploadIdentificationTextDataReqDTO reqDTO);
}