package com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.SchoolFriendIdentificationMapper;
import com.ShiXi.common.service.OSSUploadService;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.dto.SchoolFriendUploadIdentificationTextDataReqDTO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.domin.vo.SchoolFriendGetIdentificationDataVO;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.entity.SchoolFriendIdentification;
import com.ShiXi.user.IdentityAuthentication.schoolFriendIdentification.service.SchoolFriendIdentificationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Slf4j
@Service
public class SchoolFriendIdentificationServiceImpl extends ServiceImpl<SchoolFriendIdentificationMapper, SchoolFriendIdentification> implements SchoolFriendIdentificationService {

    @Resource
    IdentificationService identificationService;

    @Resource
    OSSUploadService ossPictureService;

    // 校友认证材料存储目录
    private static final String SCHOOL_FRIEND_IDENTIFICATION_PICTURE_MATERIAL_URL = "school_friend_picture_material_url/";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationPictureData(MultipartFile file) {
        //获取用户id
        Long userId = UserHolder.getUser().getId();
        //删除旧的认证
        SchoolFriendIdentification schoolFriendIdentification = lambdaQuery().eq(SchoolFriendIdentification::getUserId, userId).one();
        if (schoolFriendIdentification != null) {
            String url = schoolFriendIdentification.getPictureMaterialUrl();
            ossPictureService.deletePicture(url);
        }
        //上传新的认证
        String url = ossPictureService.uploadPicture(file, SCHOOL_FRIEND_IDENTIFICATION_PICTURE_MATERIAL_URL);
        LambdaUpdateWrapper<SchoolFriendIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SchoolFriendIdentification::getUserId, userId);
        updateWrapper.set(SchoolFriendIdentification::getPictureMaterialUrl, url);
        update(updateWrapper);
        //设置对应的审核状态：待审核
        LambdaUpdateWrapper<Identification> statusUpdateWrapper = new LambdaUpdateWrapper<>();
        statusUpdateWrapper.eq(Identification::getUserId, userId)
                .set(Identification::getIsSchoolFriend, 1);
        identificationService.update(statusUpdateWrapper);
        return Result.ok();
    }

    @Override
    public Result getMyIdentificationData() {
        //查询用户id
        Long userId = UserHolder.getUser().getId();
        //查询该用户的校友身份上传资料
        SchoolFriendIdentification schoolFriendIdentification = lambdaQuery()
                .eq(SchoolFriendIdentification::getUserId, userId)
                .one();
        //判空
        if (schoolFriendIdentification == null) {
            return Result.fail("出现错误");
        }
        //构造vo对象
        SchoolFriendGetIdentificationDataVO schoolFriendGetIdentificationDataVO = BeanUtil.toBean(schoolFriendIdentification, SchoolFriendGetIdentificationDataVO.class);
        schoolFriendGetIdentificationDataVO.setIdentification(2);
        return Result.ok(schoolFriendGetIdentificationDataVO);
    }

    @Override
    public SchoolFriendGetIdentificationDataVO getIdentificationDataByUserId(Long userId) {
        //查询该用户的校友身份上传资料
        SchoolFriendIdentification schoolFriendIdentification = lambdaQuery()
                .eq(SchoolFriendIdentification::getUserId, userId)
                .one();
        //判空
        if (schoolFriendIdentification == null) {
            return null;
        }
        //构造vo对象
        SchoolFriendGetIdentificationDataVO schoolFriendGetIdentificationDataVO = BeanUtil.toBean(schoolFriendIdentification, SchoolFriendGetIdentificationDataVO.class);
        schoolFriendGetIdentificationDataVO.setIdentification(2);
        return schoolFriendGetIdentificationDataVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result uploadIdentificationTextData(SchoolFriendUploadIdentificationTextDataReqDTO reqDTO) {
        Long userId = UserHolder.getUser().getId();
        SchoolFriendIdentification schoolFriendIdentification = BeanUtil.copyProperties(reqDTO, SchoolFriendIdentification.class);
        schoolFriendIdentification.setUserId(userId);
        LambdaUpdateWrapper<SchoolFriendIdentification> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SchoolFriendIdentification::getUserId, userId);
        update(schoolFriendIdentification, updateWrapper);
        identificationService.lambdaUpdate()
                .eq(Identification::getUserId, userId)
                .set(Identification::getIsSchoolFriend, 1)
                .update();
        log.info("用户[{}]校友身份认证信息上传成功", userId);
        identificationService.notifyAdminToAudit(2);
        return Result.ok();
    }
}