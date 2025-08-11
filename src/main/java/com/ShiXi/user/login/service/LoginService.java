package com.ShiXi.user.login.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.user.common.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LoginService extends IService<User> {
    Result sendCode(String phone);

    Result loginByPhone(String phone, String code);

    Result loginByWechat(String code, String phone);

    Result logout();
}
