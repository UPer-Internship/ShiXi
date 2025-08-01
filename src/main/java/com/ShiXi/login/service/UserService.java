package com.ShiXi.login.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.login.domin.dto.UserDTO;
import com.ShiXi.login.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    Result loginByAccount(String account, String password);

    Result sendCode(String phone);

    Result loginByPhone(String phone, String code);

    Result changeUserInfo(UserDTO userDTO);

    Result loginByWechat(String code);

    Result getUserInfoById(Long id);
}
