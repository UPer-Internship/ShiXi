package com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.TeacherIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.dto.TeacherTeamUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.domin.vo.TeacherTeamGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.entity.TeacherTeamIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.service.TeacherTeamIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class TeacherTeamIdentificationServiceImpl extends ServiceImpl<TeacherIdentificationMapper, TeacherTeamIdentification> implements TeacherTeamIdentificationService {

    @Resource
    OSSUploadService ossPictureService;

    @Resource
    private TeacherTeamIdentificationService teacherTeamIdentificationService;

    private static final String TEACHER_ID_CARD_DIR = "teacherIdCard/";
    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的教师团队上传资料
        TeacherTeamIdentification teacherIdentification = lambdaQuery()
                .eq(TeacherTeamIdentification::getUserId, userId)
                .one();
        //判空
        if (teacherIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        TeacherTeamGetIdentificationDataVO teacherTeamGetIdentificationDataVO = new TeacherTeamGetIdentificationDataVO();
        teacherTeamGetIdentificationDataVO.setName(teacherIdentification.getName())
                .setSchoolName(teacherIdentification.getSchoolName())
                .setMajor(teacherIdentification.getMajor())
                .setTeacherIdCardUrl(teacherIdentification.getTeacherIdCard());
        return Result.ok(teacherTeamGetIdentificationDataVO);
    }

    @Override
    public Result getIdentificationDataByUserId(Integer userId) {
        TeacherTeamIdentification teacherIdentification = teacherTeamIdentificationService.lambdaQuery()
                .eq(TeacherTeamIdentification::getUserId, userId)
                .one();
        //判空
        if (teacherIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        TeacherTeamGetIdentificationDataVO teacherTeamGetIdentificationDataVO = new TeacherTeamGetIdentificationDataVO();
        teacherTeamGetIdentificationDataVO.setName(teacherIdentification.getName())
                .setSchoolName(teacherIdentification.getSchoolName())
                .setMajor(teacherIdentification.getMajor())
                .setTeacherIdCardUrl(teacherIdentification.getTeacherIdCard());
        return Result.ok(teacherTeamGetIdentificationDataVO);
    }


    @Override
    public Result uploadIdentificationPictureData(String type, MultipartFile file) {
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        String url = "";
        boolean success = false;
        if (type.equals("teacherIdCard")) {
            // 删除旧的认证
            TeacherTeamIdentification teacherIdentification = lambdaQuery().eq(TeacherTeamIdentification::getUserId, userId).one();
            url = teacherIdentification.getTeacherIdCard();
            ossPictureService.deletePicture(url);
            // 上传新的认证
            url = ossPictureService.uploadPicture(file, TEACHER_ID_CARD_DIR);
            LambdaUpdateWrapper<TeacherTeamIdentification> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(TeacherTeamIdentification::getUserId, userId);
            updateWrapper.set(TeacherTeamIdentification::getTeacherIdCard, url);
            success = update(updateWrapper);
        }
        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result uploadIdentificationTextData(TeacherTeamUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        LambdaUpdateWrapper<TeacherTeamIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeacherTeamIdentification::getUserId, userId);
        if (reqDTO.getName() != null) {
            updateWrapper.set(TeacherTeamIdentification::getName, reqDTO.getName());
        }
        if (reqDTO.getSchoolName() != null) {
            updateWrapper.set(TeacherTeamIdentification::getSchoolName, reqDTO.getSchoolName());
        }
        if (reqDTO.getMajor() != null) {
            updateWrapper.set(TeacherTeamIdentification::getMajor, reqDTO.getMajor());
        }
        boolean success = update(updateWrapper);
        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }
}
