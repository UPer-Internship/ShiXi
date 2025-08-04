package com.ShiXi.user.IdentityAuthentication.studentIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.common.domin.dto.InitiateIdentificationDTO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface StudentIdentificationService extends IService<StudentIdentification> {
    Result toIdentification(String type , MultipartFile file);

    Result getMyIdentification(String identification, String type);

    Result getIdentificationDataByUserId(Integer userId,String identification, String type);
}
