package com.ShiXi.user.IdentityAuthentication.studentIdentification.service.impl;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.StudentIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class StudentIdentificationServiceImpl extends ServiceImpl<StudentIdentificationMapper, StudentIdentification> implements StudentIdentificationService {

    @Resource
    IdentificationService identificationService;

    @Resource
    OSSUploadService ossPictureService;

    @Resource
    private StudentIdentificationService studentIdentificationService;

    @Value("${spring.oss.bucketName}")
    private String bucketName;

    // 头像存储目录
    private static final String IDENTITY_CARD_DIR = "identityCard/";

    private static final String GRADUATION_CERTIFICATE_DIR = "graduationCertificate/";

    private static final String STUDENT_ID_CARD_DIR = "studentIDCard/";
    @Override
    @Transactional
    public Result uploadIdentificationPictureData(String type, MultipartFile file) {
        //获取用户id
        Long userId = UserHolder.getUser().getId();
        String url = "";
        boolean success=false;
            if(type.equals("identityCard")){
                //删除旧的认证
                StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
                url = studentIdentification.getIdentityCard();
                ossPictureService.deletePicture(url);
                //上传新的认证
                url = ossPictureService.uploadPicture(file, IDENTITY_CARD_DIR);
                LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(StudentIdentification::getUserId, userId);
                updateWrapper.set(StudentIdentification::getStudentIdCard,url);
                //设置对应的审核状态：待审核
                LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
                statusUpdateWrapper.eq(Identification::getUserId, userId)
                        .set(Identification::getIsStudent, 1);
                success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
            }
            else if(type.equals("graduationCertificate")){
                StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
                url = studentIdentification.getGraduationCertificate();
                ossPictureService.deletePicture(url);
                url = ossPictureService.uploadPicture(file, GRADUATION_CERTIFICATE_DIR);
                LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(StudentIdentification::getUserId, userId);
                updateWrapper.set(StudentIdentification::getGraduationCertificate,url);
                //设置对应的审核状态：待审核
                LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
                statusUpdateWrapper.eq(Identification::getUserId, userId)
                        .set(Identification::getIsStudent, 1);
                success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
            }
            else if(type.equals("studentIDCard")){
                StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
                url = studentIdentification.getStudentIdCard();
                ossPictureService.deletePicture(url);
                url = ossPictureService.uploadPicture(file, STUDENT_ID_CARD_DIR);
                LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(StudentIdentification::getUserId, userId);
                updateWrapper.set(StudentIdentification::getStudentIdCard,url);
                //设置对应的审核状态：待审核
                LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
                statusUpdateWrapper.eq(Identification::getUserId, userId)
                        .set(Identification::getIsStudent, 1);
                success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
            }

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
        }

    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的学生身份上传资料
        StudentIdentification studentIdentification = lambdaQuery()
                .eq(StudentIdentification::getUserId, userId)
                .one();
        //判空
        if (studentIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        StudentGetIdentificationDataVO studentGetIdentificationDataVO = new StudentGetIdentificationDataVO();
        studentGetIdentificationDataVO.setIdentityCardUrl(studentIdentification.getIdentityCard())
                .setStudentIdCardUrl(studentIdentification.getStudentIdCard())
                .setGraduationCertificateUrl(studentIdentification.getGraduationCertificate());

        return Result.ok(studentGetIdentificationDataVO);
    }

    @Override
    public StudentGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        //查询该用户的学生身份上传资料
        StudentIdentification studentIdentification = lambdaQuery()
                .eq(StudentIdentification::getUserId, userId)
                .one();
        //判空
        if (studentIdentification == null) {
            return null;
        }
        //构造vo对象
        StudentGetIdentificationDataVO studentGetIdentificationDataVO = new StudentGetIdentificationDataVO();
        studentGetIdentificationDataVO.setIdentityCardUrl(studentIdentification.getIdentityCard())
                .setStudentIdCardUrl(studentIdentification.getStudentIdCard())
                .setUserId(userId)
                .setIdentification("student")
                .setGraduationCertificateUrl(studentIdentification.getGraduationCertificate());

        return studentGetIdentificationDataVO;
    }
}



