package com.ShiXi.user.IdentityAuthentication.studentIdentification.service.impl;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.StudentIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.service.StudentIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    public Result toIdentification(String type,MultipartFile file) {
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
                success = update(updateWrapper);
            }
            else if(type.equals("graduationCertificate")){
                StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
                url = studentIdentification.getGraduationCertificate();
                ossPictureService.deletePicture(url);
                url = ossPictureService.uploadPicture(file, GRADUATION_CERTIFICATE_DIR);
                LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(StudentIdentification::getUserId, userId);
                updateWrapper.set(StudentIdentification::getGraduationCertificate,url);
                success = update(updateWrapper);
            }
            else if(type.equals("studentIDCard")){
                StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
                url = studentIdentification.getStudentIdCard();
                ossPictureService.deletePicture(url);
                url = ossPictureService.uploadPicture(file, STUDENT_ID_CARD_DIR);
                LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(StudentIdentification::getUserId, userId);
                updateWrapper.set(StudentIdentification::getStudentIdCard,url);
                success = update(updateWrapper);
            }

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
        }

    @Override
    public Result getMyIdentification(String type) {
        Long userId = UserHolder.getUser().getId();
        StudentIdentification studentIdentification = studentIdentificationService.lambdaQuery()
                .eq(StudentIdentification::getUserId, userId)
                .one();
        if(studentIdentification==null){
            return Result.fail("出现错误");
        }
        if(type.equals("identityCard")){
            String url = studentIdentification.getIdentityCard();
            if(url==null||url.equals("")){
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        else if(type.equals("graduationCertificate")){
            String url = studentIdentification.getGraduationCertificate();
            if(url==null||url.equals("")){
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        else if(type.equals("studentIdCard")){
            String url = studentIdentification.getStudentIdCard();
            if(url==null||url.equals("")){
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        return Result.fail("未知错误");
    }

    @Override
    public Result getIdentificationDataByUserId(Integer userId,String identification, String type) {
        StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
        if(studentIdentification==null){
            return Result.fail("此用户无此数据");
        }
        if(type.equals("identityCard")){
            String url = studentIdentification.getIdentityCard();
            if(url==null||url.equals("")){
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        else if(type.equals("graduationCertificate")){
            String url = studentIdentification.getGraduationCertificate();
            if(url==null||url.equals("")){
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        else if(type.equals("studentIdCard")){
            String url = studentIdentification.getStudentIdCard();
            if(url==null||url.equals("")){
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        return Result.fail("未知错误");
    }

//    @Override
//    public Result changeIdentificationData(String identification) {
//        UserHolder.getUser().getId();
//        identificationService.lambdaUpdate().eq(Identification::getUserId, UserHolder.getUser().getId())
//                .set(Identification::getIdentification, identification)
//                .update();
//        return null;
//    }
}



