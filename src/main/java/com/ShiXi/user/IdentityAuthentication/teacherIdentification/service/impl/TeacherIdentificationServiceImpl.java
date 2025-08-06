package com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.TeacherIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity.TeacherIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class TeacherIdentificationServiceImpl extends ServiceImpl<TeacherIdentificationMapper, TeacherIdentification> implements TeacherIdentificationService {

    @Resource
    OSSUploadService ossPictureService;

    @Resource
    private TeacherIdentificationService teacherIdentificationService;

    @Value("${spring.oss.bucketName}")
    private String bucketName;

    // 存储目录
    private static final String TEACHER_ID_CARD_DIR = "teacherIdCard/";

    @Override
    public Result toIdentification(String type, MultipartFile file) {
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        String url = "";
        boolean success = false;
        
        if (type.equals("teacherIdCard")) {
            // 删除旧的认证
            TeacherIdentification teacherIdentification = lambdaQuery().eq(TeacherIdentification::getUserId, userId).one();
            url = teacherIdentification.getTeacherIdCard();
            ossPictureService.deletePicture(url);
            // 上传新的认证
            url = ossPictureService.uploadPicture(file, TEACHER_ID_CARD_DIR);
            LambdaUpdateWrapper<TeacherIdentification> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TeacherIdentification::getUserId, userId);
            updateWrapper.set(TeacherIdentification::getTeacherIdCard, url);
            success = update(updateWrapper);
        }

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result getMyIdentification(String identification, String type) {
        Long userId = UserHolder.getUser().getId();
        TeacherIdentification teacherIdentification = teacherIdentificationService.lambdaQuery()
                .eq(TeacherIdentification::getUserId, userId)
                .one();
        if (teacherIdentification == null) {
            return Result.fail("出现错误");
        }
        
        if (type.equals("teacherIdCard")) {
            String url = teacherIdentification.getTeacherIdCard();
            if (url == null || url.equals("")) {
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        
        return Result.fail("未知错误");
    }

    @Override
    public Result getIdentificationDataByUserId(Integer userId, String identification, String type) {
        TeacherIdentification teacherIdentification = lambdaQuery().eq(TeacherIdentification::getUserId, userId).one();
        if (teacherIdentification == null) {
            return Result.fail("此用户无此数据");
        }
        
        if (type.equals("teacherIdCard")) {
            String url = teacherIdentification.getTeacherIdCard();
            if (url == null || url.equals("")) {
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        
        return Result.fail("未知错误");
    }
}
