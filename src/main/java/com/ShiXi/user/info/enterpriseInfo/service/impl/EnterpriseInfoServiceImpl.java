package com.ShiXi.user.info.enterpriseInfo.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.EnterpriseInfoMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.user.info.enterpriseInfo.domin.dto.EnterpriseChangeInfoDTO;
import com.ShiXi.user.info.enterpriseInfo.domin.vo.EnterpriseInfoVO;
import com.ShiXi.user.info.enterpriseInfo.entity.EnterpriseInfo;
import com.ShiXi.user.info.enterpriseInfo.service.EnterpriseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseInfoServiceImpl extends ServiceImpl<EnterpriseInfoMapper, EnterpriseInfo> implements EnterpriseInfoService {

    @Override
    public Result getEnterpriseInfo() {
        // 获取当前用户ID
        Long userId = UserHolder.getUser().getId();

        // 查询企业信息
        EnterpriseInfo enterpriseInfo = lambdaQuery()
                .eq(EnterpriseInfo::getUserId, userId)
                .one();

        // 如果没有企业信息，返回空对象
        if (enterpriseInfo == null) {
            return Result.ok(new EnterpriseInfoVO());
        }

        // 构造返回对象
        EnterpriseInfoVO enterpriseInfoVO = new EnterpriseInfoVO();
        enterpriseInfoVO.setUserId(enterpriseInfo.getUserId());
        enterpriseInfoVO.setEnterpriseName(enterpriseInfo.getEnterpriseName());
        enterpriseInfoVO.setYourPosition(enterpriseInfo.getYourPosition());
        enterpriseInfoVO.setEnterpriseScale(enterpriseInfo.getEnterpriseScale());
        enterpriseInfoVO.setEnterpriseIndustry(enterpriseInfo.getEnterpriseIndustry());
        enterpriseInfoVO.setEnterpriseAddress(enterpriseInfo.getEnterpriseAddress());
        enterpriseInfoVO.setEmail(enterpriseInfo.getEmail());

        return Result.ok(enterpriseInfoVO);
    }

    @Override
    public Result setMyEnterpriseInfo(EnterpriseChangeInfoDTO enterpriseChangeInfoDTO) {
        // 获取当前用户ID
        Long userId = UserHolder.getUser().getId();

        // 查询是否已存在企业信息
        EnterpriseInfo existingEnterpriseInfo = lambdaQuery()
                .eq(EnterpriseInfo::getUserId, userId)
                .one();

        // 创建或更新企业信息实体
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setEnterpriseName(enterpriseChangeInfoDTO.getEnterpriseName());
        enterpriseInfo.setYourPosition(enterpriseChangeInfoDTO.getYourPosition());
        enterpriseInfo.setEnterpriseScale(enterpriseChangeInfoDTO.getEnterpriseScale());
        enterpriseInfo.setEnterpriseIndustry(enterpriseChangeInfoDTO.getEnterpriseIndustry());
        enterpriseInfo.setEnterpriseAddress(enterpriseChangeInfoDTO.getEnterpriseAddress());
        enterpriseInfo.setEmail(enterpriseChangeInfoDTO.getEmail());

        if (existingEnterpriseInfo != null) {
            // 更新已有记录
            enterpriseInfo.setId(existingEnterpriseInfo.getId());
            enterpriseInfo.setUserId(userId);
            updateById(enterpriseInfo);
        } else {
            // 新增记录
            enterpriseInfo.setUserId(userId);
            save(enterpriseInfo);
        }

        return Result.ok();
    }
}
