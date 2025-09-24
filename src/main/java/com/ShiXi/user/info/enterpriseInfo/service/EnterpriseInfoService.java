package com.ShiXi.user.info.enterpriseInfo.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.info.enterpriseInfo.domin.dto.EnterpriseChangeInfoDTO;
import com.ShiXi.user.info.enterpriseInfo.entity.EnterpriseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface EnterpriseInfoService extends IService<EnterpriseInfo> {
    Result getEnterpriseInfo();

    Result setMyEnterpriseInfo(EnterpriseChangeInfoDTO enterpriseChangeInfoDTO);
}
