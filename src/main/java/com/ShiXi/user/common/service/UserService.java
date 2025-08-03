package com.ShiXi.user.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.domin.dto.UserDTO;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    Result loginByAccount(String account, String password);

    Result sendCode(String phone);

    Result loginByPhone(String phone, String code);

    Result changeUserInfo(UserDTO userDTO);

    Result loginByWechat(String code);

    Result getUserInfoById(Long id);

//    User getUserById(Long id);
    //获取自己的用户信息
    User getMyUserInfo();

//    Result getIdentification();
}
