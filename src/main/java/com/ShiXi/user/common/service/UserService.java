package com.ShiXi.user.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.domin.dto.ChangeInfoDTO;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {
    Result loginByAccount(String account, String password);

    Result sendCode(String phone);

    Result loginByPhone(String phone, String code);

    Result changeMyUserInfo(ChangeInfoDTO changeInfoDTO);

    Result loginByWechat(String code);

    Result getUserInfoById(Long id);

//    User getUserById(Long id);
    //获取自己的用户信息
    Result getMyUserInfo();

//    Result getIdentification();
    
    Result getUserInfoByUuid(String uuid);

    Result getRegion();

    Result getMajorList();

    Result getIndustryList();

    Result getUniversityList();

    Result changeMyIcon(MultipartFile file);

    Result setUniversityList();
}
