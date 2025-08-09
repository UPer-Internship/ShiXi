package com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.StudentTeamIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.dto.StudentTeamUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.domin.vo.StudentTeamGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.entity.StudentTeamIdentification;
import com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.service.StudentTeamIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class StudentTeamIdentificationServiceImpl extends ServiceImpl<StudentTeamIdentificationMapper, StudentTeamIdentification> implements StudentTeamIdentificationService {

    @Resource
    OSSUploadService ossPictureService;

    @Resource
    private IdentificationService identificationService;

    private static final String STUDENT_TEAM_IDENTIFICATION_DIR = "studentTeamIdentification/";

    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的学生团队上传资料
        StudentTeamIdentification studentTeamIdentification = lambdaQuery()
                .eq(StudentTeamIdentification::getUserId, userId)
                .one();
        //判空
        if (studentTeamIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        StudentTeamGetIdentificationDataVO studentTeamGetIdentificationDataVO = new StudentTeamGetIdentificationDataVO();
        studentTeamGetIdentificationDataVO.setTeamName(studentTeamIdentification.getTeamName())
                .setUniversityName(studentTeamIdentification.getUniversityName())
                .setSchoolName(studentTeamIdentification.getSchoolName())
                .setTeamLeaderName(studentTeamIdentification.getTeamLeaderName())
                .setTeamLeaderGender(studentTeamIdentification.getTeamLeaderGender())
                .setAddress(studentTeamIdentification.getAddress())
                .setIdentificationImagesUrl(studentTeamIdentification.getIdentificationImages());
        return Result.ok(studentTeamGetIdentificationDataVO);
    }

    @Override
    public StudentTeamGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        StudentTeamIdentification studentTeamIdentification = lambdaQuery()
                .eq(StudentTeamIdentification::getUserId, userId)
                .one();
        //判空
        if (studentTeamIdentification == null) {
            return null;
        }
        //构造vo对象
        StudentTeamGetIdentificationDataVO studentTeamGetIdentificationDataVO = new StudentTeamGetIdentificationDataVO();
        studentTeamGetIdentificationDataVO.setTeamName(studentTeamIdentification.getTeamName())
                .setUniversityName(studentTeamIdentification.getUniversityName())
                .setSchoolName(studentTeamIdentification.getSchoolName())
                .setTeamLeaderName(studentTeamIdentification.getTeamLeaderName())
                .setTeamLeaderGender(studentTeamIdentification.getTeamLeaderGender())
                .setAddress(studentTeamIdentification.getAddress())
                .setUserId(userId)
                .setIdentification("studentTeam")
                .setIdentificationImagesUrl(studentTeamIdentification.getIdentificationImages());
        return studentTeamGetIdentificationDataVO;
    }

    @Override
    public Result uploadIdentificationPictureData(String type, MultipartFile file) {
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        String url = "";
        boolean success = false;
        if (type.equals("identificationImages")) {
            // 删除旧的认证
            StudentTeamIdentification studentTeamIdentification = lambdaQuery().eq(StudentTeamIdentification::getUserId, userId).one();
            url = studentTeamIdentification.getIdentificationImages();
            ossPictureService.deletePicture(url);
            // 上传新的认证
            url = ossPictureService.uploadPicture(file, STUDENT_TEAM_IDENTIFICATION_DIR);
            LambdaUpdateWrapper<StudentTeamIdentification> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StudentTeamIdentification::getUserId, userId);
            updateWrapper.set(StudentTeamIdentification::getIdentificationImages, url);
            //设置对应的审核状态：待审核
            LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
            statusUpdateWrapper.eq(Identification::getUserId, userId)
                    .set(Identification::getIsStudentTeam, 1);
            success = update(updateWrapper) && identificationService.update(statusUpdateWrapper);
        }
        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result uploadIdentificationTextData(StudentTeamUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        LambdaUpdateWrapper<StudentTeamIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StudentTeamIdentification::getUserId, userId);
        if (reqDTO.getTeamName() != null) {
            updateWrapper.set(StudentTeamIdentification::getTeamName, reqDTO.getTeamName());
        }
        if (reqDTO.getUniversityName() != null) {
            updateWrapper.set(StudentTeamIdentification::getUniversityName, reqDTO.getUniversityName());
        }
        if (reqDTO.getSchoolName() != null) {
            updateWrapper.set(StudentTeamIdentification::getSchoolName, reqDTO.getSchoolName());
        }
        if (reqDTO.getTeamLeaderName() != null) {
            updateWrapper.set(StudentTeamIdentification::getTeamLeaderName, reqDTO.getTeamLeaderName());
        }
        if (reqDTO.getTeamLeaderGender() != null) {
            updateWrapper.set(StudentTeamIdentification::getTeamLeaderGender, reqDTO.getTeamLeaderGender());
        }
        if (reqDTO.getAddress() != null) {
            updateWrapper.set(StudentTeamIdentification::getAddress, reqDTO.getAddress());
        }
        //设置对应的审核状态：待审核
        LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
        statusUpdateWrapper.eq(Identification::getUserId, userId)
                .set(Identification::getIsStudentTeam, 1);
        boolean success = update(updateWrapper) && identificationService.update(statusUpdateWrapper);

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }
}