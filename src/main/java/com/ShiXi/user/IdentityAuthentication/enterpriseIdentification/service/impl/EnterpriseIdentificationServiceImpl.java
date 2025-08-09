package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.EnterpriseIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.dto.EnterpriseUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.domin.vo.EnterpriseGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.domin.vo.StudentGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.studentIdentification.entity.StudentIdentification;
import com.ShiXi.user.IdentityAuthentication.teacherTeamIdentification.entity.TeacherTeamIdentification;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class EnterpriseIdentificationServiceImpl extends ServiceImpl<EnterpriseIdentificationMapper, EnterpriseIdentification> implements EnterpriseIdentificationService {

    @Resource
    private  OSSUploadService ossPictureService;
    @Resource
    private IdentificationService identificationService;
    // 存储目录
    private static final String ENTERPRISE_ID_CARD_DIR = "enterpriseIdCard/";

    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的学生身份上传资料
        EnterpriseIdentification enterpriseIdentification = lambdaQuery()
                .eq(EnterpriseIdentification::getUserId, userId)
                .one();
        //判空
        if (enterpriseIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        EnterpriseGetIdentificationDataVO enterpriseGetIdentificationDataVO = new EnterpriseGetIdentificationDataVO();
        enterpriseGetIdentificationDataVO.setEnterpriseScale(enterpriseIdentification.getEnterpriseScale())
                .setEnterpriseName(enterpriseIdentification.getEnterpriseName())
                .setEnterpriseType(enterpriseIdentification.getEnterpriseType())
                .setEnterpriseIdCard(enterpriseIdentification.getEnterpriseIdCard());

        return Result.ok(enterpriseGetIdentificationDataVO);
    }

    @Override
    public Result uploadIdentificationTextData(EnterpriseUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        LambdaUpdateWrapper<EnterpriseIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(EnterpriseIdentification::getUserId, userId);
        if (reqDTO.getEnterpriseName() != null) {
            updateWrapper.set(EnterpriseIdentification::getEnterpriseName, reqDTO.getEnterpriseName());
        }
        if (reqDTO.getEnterpriseScale() != null) {
            updateWrapper.set(EnterpriseIdentification::getEnterpriseScale, reqDTO.getEnterpriseScale());
        }
        if (reqDTO.getEnterpriseType() != null) {
            updateWrapper.set(EnterpriseIdentification::getEnterpriseType, reqDTO.getEnterpriseType());
        }
        //设置对应的审核状态：待审核
        LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
        statusUpdateWrapper.eq(Identification::getUserId, userId)
                .set(Identification::getIsEnterprise, 1);
        boolean success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

    @Override
    public EnterpriseGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        //查询该用户的学生身份上传资料
        EnterpriseIdentification enterpriseIdentification = lambdaQuery()
                .eq(EnterpriseIdentification::getUserId, userId)
                .one();
        //判空
        if (enterpriseIdentification == null) {
            return null;
        }
        //构造vo对象
        EnterpriseGetIdentificationDataVO enterpriseGetIdentificationDataVO = new EnterpriseGetIdentificationDataVO();
        enterpriseGetIdentificationDataVO.setEnterpriseScale(enterpriseIdentification.getEnterpriseScale())
                .setEnterpriseName(enterpriseIdentification.getEnterpriseName())
                .setEnterpriseType(enterpriseIdentification.getEnterpriseType())
                .setEnterpriseIdCard(enterpriseIdentification.getEnterpriseIdCard());

        return enterpriseGetIdentificationDataVO;
    }

    @Override
    public Result uploadIdentificationPictureData(String type, MultipartFile file) {
                // 获取用户id
        Long userId = UserHolder.getUser().getId();
        String url = "";
        boolean success = false;
        if (type.equals("enterpriseIdCard")) {
            // 删除旧的认证
            EnterpriseIdentification enterpriseIdentification = lambdaQuery().eq(EnterpriseIdentification::getUserId, userId).one();
            url = enterpriseIdentification.getEnterpriseIdCard();
            ossPictureService.deletePicture(url);
            // 上传新的认证
            url = ossPictureService.uploadPicture(file, ENTERPRISE_ID_CARD_DIR);
            LambdaUpdateWrapper<EnterpriseIdentification> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(EnterpriseIdentification::getUserId, userId);
            updateWrapper.set(EnterpriseIdentification::getEnterpriseIdCard, url);
            //设置对应的审核状态：待审核
            LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
            statusUpdateWrapper.eq(Identification::getUserId, userId)
                    .set(Identification::getIsEnterprise, 1);
            success = update(updateWrapper)&&identificationService.update(statusUpdateWrapper);
        }

        if (success) {
            return Result.ok();
        }
        return Result.fail("更新失败");
    }

}
