package com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.dto.TeacherTeamUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.entity.TeacherTeamIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherTeamIdentificationService extends IService<TeacherTeamIdentification> {
    /**
     * 获取我作为教师身份的验证资料
     * @return 上传成功与否
     */
    Result getMyIdentificationData();
    /**
     * 根据id获取教师的身份资料
     * @param userId 用户id
     * @return 身份信息
     */
    Result getIdentificationDataByUserId(Integer userId);
    /**
     * 上传教师身份图片类身份验证资料
     * @param type 身份信息的种类
     * @param file 图片资料
     * @return 上传成功与否
     */
    Result uploadIdentificationPictureData(String type, MultipartFile file);
    /**
     * 上传教师身份文本类身份验证资料
     * @param reqDTO 身份信息的种类
     * @return 上传成功与否
     */
    Result uploadIdentificationTextData(TeacherTeamUploadIdentificationTextDataReqDTO reqDTO);
}
