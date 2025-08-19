package com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.TeacherIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.dto.TeacherUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.domin.vo.TeacherGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.entity.TeacherIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.TeacherIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class TeacherIdentificationServiceImpl extends ServiceImpl<TeacherIdentificationMapper, TeacherIdentification> implements TeacherIdentificationService {

    @Resource
    OSSUploadService ossPictureService;

    @Resource
    private IdentificationService identificationService;

    private static final String TEACHER_ID_CARD_DIR = "teacherIdCard/";
    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的教师团队上传资料
        TeacherIdentification teacherIdentification = lambdaQuery()
                .eq(TeacherIdentification::getUserId, userId)
                .one();
        //判空
        if (teacherIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        TeacherGetIdentificationDataVO teacherTeamGetIdentificationDataVO = new TeacherGetIdentificationDataVO();
        teacherTeamGetIdentificationDataVO.setName(teacherIdentification.getName())
                .setSchoolName(teacherIdentification.getSchoolName())
                .setMajor(teacherIdentification.getMajor())
                .setTeacherIdCardUrl(teacherIdentification.getTeacherIdCard());
        return Result.ok(teacherTeamGetIdentificationDataVO);
    }

    @Override
    public TeacherGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        TeacherIdentification teacherIdentification = lambdaQuery()
                .eq(TeacherIdentification::getUserId, userId)
                .one();
        //判空
        if (teacherIdentification == null) {
            return null;
        }
        //构造vo对象
        TeacherGetIdentificationDataVO teacherTeamGetIdentificationDataVO = new TeacherGetIdentificationDataVO();
        teacherTeamGetIdentificationDataVO.setName(teacherIdentification.getName())
                .setSchoolName(teacherIdentification.getSchoolName())
                .setMajor(teacherIdentification.getMajor())
                .setUserId(userId)
                .setIdentification("teacher")
                .setTeacherIdCardUrl(teacherIdentification.getTeacherIdCard());
        return teacherTeamGetIdentificationDataVO;
    }


    @Override
    public Result uploadIdentificationPictureData(String type, MultipartFile file) {
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
            //设置对应的审核状态：待审核
            LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
            statusUpdateWrapper.eq(Identification::getUserId, userId)
                    .set(Identification::getIsTeacher, 1);
            success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
        }
        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public Result uploadIdentificationTextData(TeacherUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        LambdaUpdateWrapper<TeacherIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeacherIdentification::getUserId, userId);
        if (reqDTO.getName() != null) {
            updateWrapper.set(TeacherIdentification::getName, reqDTO.getName());
        }
        if (reqDTO.getSchoolName() != null) {
            updateWrapper.set(TeacherIdentification::getSchoolName, reqDTO.getSchoolName());
        }
        if (reqDTO.getMajor() != null) {
            updateWrapper.set(TeacherIdentification::getMajor, reqDTO.getMajor());
        }
        //设置对应的审核状态：待审核
        LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
        statusUpdateWrapper.eq(Identification::getUserId, userId)
                .set(Identification::getIsTeacher, 1);
        boolean success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }
}
