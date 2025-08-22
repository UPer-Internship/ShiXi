package com.ShiXi.user.IdentityAuthentication.studentIdentification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.StudentIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.dto.StudentUploadIdentificationTextDataReqDTO;
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

    // 头像存储目录
    private static final String STUDENT_IDENTIFICATION_PICTURE_MATERIAL_URL = "student_picture_material_url/";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationPictureData(MultipartFile file) {
        //获取用户id
        Long userId = UserHolder.getUser().getId();
        //删除旧的认证
        StudentIdentification studentIdentification = lambdaQuery().eq(StudentIdentification::getUserId, userId).one();
        String url = studentIdentification.getPictureMaterialUrl();
        ossPictureService.deletePicture(url);
        //上传新的认证
        url = ossPictureService.uploadPicture(file, STUDENT_IDENTIFICATION_PICTURE_MATERIAL_URL);
        LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StudentIdentification::getUserId, userId);
        updateWrapper.set(StudentIdentification::getPictureMaterialUrl, url);
        //设置对应的审核状态：待审核
        LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
        statusUpdateWrapper.eq(Identification::getUserId, userId)
                .set(Identification::getIsStudent, 1);
        return Result.ok();

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
        StudentGetIdentificationDataVO studentGetIdentificationDataVO = BeanUtil.toBean(studentIdentification, StudentGetIdentificationDataVO.class);
        studentGetIdentificationDataVO.setIdentification(1);
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
        StudentGetIdentificationDataVO studentGetIdentificationDataVO = BeanUtil.toBean(studentIdentification, StudentGetIdentificationDataVO.class);
        studentGetIdentificationDataVO.setIdentification(1);
        return studentGetIdentificationDataVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationTextData(StudentUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        StudentIdentification studentIdentification = BeanUtil.copyProperties(reqDTO, StudentIdentification.class);
        studentIdentification.setUserId(userId);
        LambdaUpdateWrapper<StudentIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StudentIdentification::getUserId, userId);
        update(studentIdentification, updateWrapper);
        identificationService.lambdaUpdate()
                .eq(Identification::getUserId, userId)
                .set(Identification::getIsStudent, 1)
                .update();
        log.info("用户[{}]学生身份认证信息上传成功", userId);
        identificationService.notifyAdminToAudit(1);
        return Result.ok();
    }


}



