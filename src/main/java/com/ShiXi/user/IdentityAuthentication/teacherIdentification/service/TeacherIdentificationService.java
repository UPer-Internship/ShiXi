package com.ShiXi.user.IdentityAuthentication.teacherIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity.TeacherIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherIdentificationService extends IService<TeacherIdentification> {
    Result toIdentification(String type, MultipartFile file);

    Result getMyIdentification(String identification, String type);

    Result getIdentificationDataByUserId(Integer userId, String identification, String type);
}
