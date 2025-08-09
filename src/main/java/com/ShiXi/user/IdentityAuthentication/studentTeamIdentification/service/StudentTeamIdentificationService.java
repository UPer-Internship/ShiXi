package com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.dto.StudentTeamUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.vo.StudentTeamGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.entity.StudentTeamIdentification;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface StudentTeamIdentificationService extends IService<StudentTeamIdentification> {
    /**
     * 获取我作为学生团队身份的验证资料
     * @return 上传成功与否
     */
    Result getMyIdentificationData();
    
    /**
     * 根据id获取学生团队的身份资料
     * @param userId 用户id
     * @return 身份信息
     */
    StudentTeamGetIdentificationDataVO getIdentificationDataByUserId(Long userId);
    
    /**
     * 上传学生团队身份图片类身份验证资料
     * @param type 身份信息的种类
     * @param file 图片资料
     * @return 上传成功与否
     */
    Result uploadIdentificationPictureData(String type, MultipartFile file);
    
    /**
     * 上传学生团队身份文本类身份验证资料
     * @param reqDTO 身份信息的种类
     * @return 上传成功与否
     */
    Result uploadIdentificationTextData(StudentTeamUploadIdentificationTextDataReqDTO reqDTO);
}