package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.SchoolFriendIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo.SchoolFriendGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity.SchoolFriendIdentification;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
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
public class SchoolFriendIdentificationServiceImpl extends ServiceImpl<SchoolFriendIdentificationMapper, SchoolFriendIdentification> implements SchoolFriendIdentificationService {

    @Resource
    OSSUploadService ossPictureService;

    @Resource
    private IdentificationService identificationService;
    // 存储目录
    private static final String GRADUATION_CERTIFICATE_DIR = "graduationCertificate/";

    @Override
    @Transactional
    public Result uploadIdentificationPictureData(String type, MultipartFile file) {
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        String url = "";
        boolean success = false;
        
        if (type.equals("graduationCertificate")) {
            // 删除旧的认证
            SchoolFriendIdentification schoolFriendIdentification = lambdaQuery().eq(SchoolFriendIdentification::getUserId, userId).one();
            url = schoolFriendIdentification.getGraduationCertificate();
            ossPictureService.deletePicture(url);
            // 上传新的认证
            url = ossPictureService.uploadPicture(file, GRADUATION_CERTIFICATE_DIR);
            LambdaUpdateWrapper<SchoolFriendIdentification> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(SchoolFriendIdentification::getUserId, userId);
            updateWrapper.set(SchoolFriendIdentification::getGraduationCertificate, url);
            //设置对应的审核状态：待审核
            LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
            statusUpdateWrapper.eq(Identification::getUserId, userId)
                    .set(Identification::getIsSchoolFriend, 1);
            success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
        }

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result getMyIdentification() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的学生身份上传资料
        SchoolFriendIdentification schoolFriendIdentification = lambdaQuery()
                .eq(SchoolFriendIdentification::getUserId, userId)
                .one();
        //判空
        if (schoolFriendIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        SchoolFriendGetIdentificationDataVO schoolFriendGetIdentificationDataVO = new SchoolFriendGetIdentificationDataVO();
        schoolFriendGetIdentificationDataVO.setIdentityCardUrl(schoolFriendIdentification.getIdentityCard())
                .setGraduationCertificateUrl(schoolFriendIdentification.getGraduationCertificate());

        return Result.ok(schoolFriendGetIdentificationDataVO);
    }

    @Override
    public SchoolFriendGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        //查询该用户的学生身份上传资料
        SchoolFriendIdentification schoolFriendIdentification = lambdaQuery()
                .eq(SchoolFriendIdentification::getUserId, userId)
                .one();
        //判空
        if (schoolFriendIdentification == null) {
            return null;
        }
        //构造vo对象
        SchoolFriendGetIdentificationDataVO schoolFriendGetIdentificationDataVO = new SchoolFriendGetIdentificationDataVO();
        schoolFriendGetIdentificationDataVO.setIdentityCardUrl(schoolFriendIdentification.getIdentityCard())
                .setUserId(userId)
                .setIdentification("schoolFriend")
                .setGraduationCertificateUrl(schoolFriendIdentification.getGraduationCertificate());

        return schoolFriendGetIdentificationDataVO;
    }


}