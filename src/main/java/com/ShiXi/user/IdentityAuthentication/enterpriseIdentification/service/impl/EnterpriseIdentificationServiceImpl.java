package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 企业身份认证服务实现类
 */
@Slf4j
@Service
public class EnterpriseIdentificationServiceImpl extends ServiceImpl<EnterpriseIdentificationMapper, EnterpriseIdentification> implements EnterpriseIdentificationService {

    @Resource
    IdentificationService identificationService;

    @Resource
    OSSUploadService ossPictureService;

    // 企业认证图片存储目录
    private static final String ENTERPRISE_IDENTIFICATION_PICTURE_MATERIAL_URL = "enterprise_picture_material_url/";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationPictureData(MultipartFile file) {
        // 获取用户id
        Long userId = UserHolder.getUser().getId();
        
        // 查询现有的企业认证信息
        EnterpriseIdentification enterpriseIdentification = lambdaQuery()
                .eq(EnterpriseIdentification::getUserId, userId)
                .one();
        
        // 如果存在旧的认证图片，先删除
        if (enterpriseIdentification != null && enterpriseIdentification.getPictureMaterialUrl() != null) {
            String oldUrl = enterpriseIdentification.getPictureMaterialUrl();
            ossPictureService.deletePicture(oldUrl);
        }
        
        // 上传新的认证图片
        String url = ossPictureService.uploadPicture(file, ENTERPRISE_IDENTIFICATION_PICTURE_MATERIAL_URL);
        
        // 更新数据库中的图片URL
        LambdaUpdateWrapper<EnterpriseIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(EnterpriseIdentification::getUserId, userId);
        updateWrapper.set(EnterpriseIdentification::getPictureMaterialUrl, url);
        update(updateWrapper);
        
        // 设置对应的审核状态：待审核（企业认证为2）
        LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
        statusUpdateWrapper.eq(Identification::getUserId, userId)
                .set(Identification::getIsEnterprise, 1);
        identificationService.update(statusUpdateWrapper);
        
        log.info("用户[{}]企业身份认证图片上传成功", userId);
        return Result.ok();
    }

    @Override
    public Result getMyIdentificationData() {
        // 查询用户id
        Long userId = UserHolder.getUser().getId();
        
        // 查询该用户的企业身份上传资料
        EnterpriseIdentification enterpriseIdentification = lambdaQuery()
                .eq(EnterpriseIdentification::getUserId, userId)
                .one();
        
        // 判空
        if (enterpriseIdentification == null) {
            return Result.fail("出现错误");
        }
        
        // 构造vo对象
        EnterpriseGetIdentificationDataVO enterpriseGetIdentificationDataVO = 
                BeanUtil.toBean(enterpriseIdentification, EnterpriseGetIdentificationDataVO.class);
        enterpriseGetIdentificationDataVO.setIdentification(2); // 企业认证标识为2
        enterpriseGetIdentificationDataVO.setPictureMaterialUrl(enterpriseIdentification.getPictureMaterialUrl());
        
        return Result.ok(enterpriseGetIdentificationDataVO);
    }

    @Override
    public EnterpriseGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        // 查询该用户的企业身份上传资料
        EnterpriseIdentification enterpriseIdentification = lambdaQuery()
                .eq(EnterpriseIdentification::getUserId, userId)
                .one();
        
        // 判空
        if (enterpriseIdentification == null) {
            return null;
        }
        
        // 构造vo对象
        EnterpriseGetIdentificationDataVO enterpriseGetIdentificationDataVO = 
                BeanUtil.toBean(enterpriseIdentification, EnterpriseGetIdentificationDataVO.class);
        enterpriseGetIdentificationDataVO.setIdentification(2); // 企业认证标识为2
        enterpriseGetIdentificationDataVO.setPictureMaterialUrl(enterpriseIdentification.getPictureMaterialUrl());
        
        return enterpriseGetIdentificationDataVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationTextData(EnterpriseUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        
        // 将DTO转换为实体对象
        EnterpriseIdentification enterpriseIdentification = BeanUtil.copyProperties(reqDTO, EnterpriseIdentification.class);
        enterpriseIdentification.setUserId(userId);
        
        // 更新企业认证信息
        LambdaUpdateWrapper<EnterpriseIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(EnterpriseIdentification::getUserId, userId);
        update(enterpriseIdentification, updateWrapper);
        
        // 设置身份认证状态（企业认证为2）
        identificationService.lambdaUpdate()
                .eq(Identification::getUserId, userId)
                .set(Identification::getIsEnterprise, 1)
                .update();
        
        log.info("用户[{}]企业身份认证信息上传成功", userId);
        
        // 通知管理员审核（企业认证类型为2）
        identificationService.notifyAdminToAudit(2);
        
        return Result.ok();
    }
}