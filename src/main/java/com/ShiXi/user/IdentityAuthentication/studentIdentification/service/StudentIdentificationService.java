package com.ShiXi.user.IdentityAuthentication.studentIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface StudentIdentificationService extends IService<StudentIdentification> {
    Result uploadIdentificationPictureData(String type , MultipartFile file);

    Result getMyIdentificationData();

    StudentGetIdentificationDataVO getIdentificationDataByUserId(Long userId);

//    Result changeIdentificationData(String identification);
}
