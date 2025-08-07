package com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.EnterpriseIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.entity.EnterpriseIdentification;
import com.ShiXi.user.IdentityAuthentication.enterpriseIdentification.service.EnterpriseIdentificationService;
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
    OSSUploadService ossPictureService;

    @Resource
    private EnterpriseIdentificationService enterpriseIdentificationService;

    @Value("${spring.oss.bucketName}")
    private String bucketName;

    // 存储目录
    private static final String ENTERPRISE_ID_CARD_DIR = "enterpriseIdCard/";

    @Override
    public Result toIdentification(String type, MultipartFile file) {
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
        EnterpriseIdentification enterpriseIdentification = enterpriseIdentificationService.lambdaQuery()
                .eq(EnterpriseIdentification::getUserId, userId)
                .one();
        if (enterpriseIdentification == null) {
            return Result.fail("出现错误");
        }
        
        if (type.equals("enterpriseIdCard")) {
            String url = enterpriseIdentification.getEnterpriseIdCard();
            if (url == null || url.equals("")) {
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        
        return Result.fail("未知错误");
    }

    @Override
    public Result getIdentificationDataByUserId(Integer userId, String identification, String type) {
        EnterpriseIdentification enterpriseIdentification = lambdaQuery().eq(EnterpriseIdentification::getUserId, userId).one();
        if (enterpriseIdentification == null) {
            return Result.fail("此用户无此数据");
        }
        
        if (type.equals("enterpriseIdCard")) {
            String url = enterpriseIdentification.getEnterpriseIdCard();
            if (url == null || url.equals("")) {
                return Result.fail("图片路径为空，请检查是否上传过资料");
            }
            return Result.ok(url);
        }
        
        return Result.fail("未知错误");
    }

    @Override
    public Result changeIdentification(String identification) {
        return null;
    }
}
