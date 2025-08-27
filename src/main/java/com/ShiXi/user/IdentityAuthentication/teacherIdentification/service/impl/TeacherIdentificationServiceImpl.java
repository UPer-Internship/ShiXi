package com.ShiXi.user.IdentityAuthentication.teacherIdentification.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class TeacherIdentificationServiceImpl extends ServiceImpl<TeacherIdentificationMapper, TeacherIdentification> implements TeacherIdentificationService {

    @Resource
    IdentificationService identificationService;

    @Resource
    OSSUploadService ossPictureService;

    // 教师认证图片存储目录
    private static final String TEACHER_IDENTIFICATION_PICTURE_MATERIAL_URL = "teacher_picture_material_url/";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationPictureData(MultipartFile file) {
        //获取用户id
        Long userId = UserHolder.getUser().getId();
        //删除旧的认证
        TeacherIdentification teacherIdentification = lambdaQuery().eq(TeacherIdentification::getUserId, userId).one();
        if (teacherIdentification != null) {
            String url = teacherIdentification.getPictureMaterialUrl(); // 修正：使用workCertificate字段
            if (url != null) {
                ossPictureService.deletePicture(url);
            }
        }
        //上传新的认证
        String url = ossPictureService.uploadPicture(file, TEACHER_IDENTIFICATION_PICTURE_MATERIAL_URL);
        LambdaUpdateWrapper<TeacherIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeacherIdentification::getUserId, userId);
        updateWrapper.set(TeacherIdentification::getPictureMaterialUrl, url); // 修正：使用workCertificate字段
        update(updateWrapper);
        //设置对应的审核状态：待审核
        identificationService.lambdaUpdate()
                .eq(Identification::getUserId, userId)
                .set(Identification::getIsTeacher, 1)
                .update();
        return Result.ok();
    }

    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的教师身份上传资料
        TeacherIdentification teacherIdentification = lambdaQuery()
                .eq(TeacherIdentification::getUserId, userId)
                .one();
        //判空
        if (teacherIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        TeacherGetIdentificationDataVO teacherGetIdentificationDataVO = BeanUtil.toBean(teacherIdentification, TeacherGetIdentificationDataVO.class);
        teacherGetIdentificationDataVO.setIdentification(3);
        return Result.ok(teacherGetIdentificationDataVO);
    }

    @Override
    public TeacherGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        //查询该用户的教师身份上传资料
        TeacherIdentification teacherIdentification = lambdaQuery()
                .eq(TeacherIdentification::getUserId, userId)
                .one();
        //判空
        if (teacherIdentification == null) {
            return null;
        }
        //构造vo对象
        TeacherGetIdentificationDataVO teacherGetIdentificationDataVO = BeanUtil.toBean(teacherIdentification, TeacherGetIdentificationDataVO.class);
        teacherGetIdentificationDataVO.setIdentification(3);
        return teacherGetIdentificationDataVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationTextData(TeacherUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        Integer isTeacher = identificationService.lambdaQuery().eq(Identification::getUserId, userId).one().getIsTeacher();
        if(isTeacher==1){
            return Result.ok("您已提交申请");
        }
        TeacherIdentification teacherIdentification = BeanUtil.copyProperties(reqDTO, TeacherIdentification.class);
        teacherIdentification.setUserId(userId);
        LambdaUpdateWrapper<TeacherIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeacherIdentification::getUserId, userId);
        update(teacherIdentification, updateWrapper);
        identificationService.lambdaUpdate()
                .eq(Identification::getUserId, userId)
                .set(Identification::getIsTeacher, 1)
                .update();
        log.info("用户[{}]教师身份认证信息上传成功", userId);
        identificationService.notifyAdminToAudit(3); // 2表示教师认证类型
        return Result.ok();
    }
}