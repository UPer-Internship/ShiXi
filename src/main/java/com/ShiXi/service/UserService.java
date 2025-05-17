package com.ShiXi.service;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    Result loginByAccount(String account, String password);

    Result sendCode(String phone);

    Result loginByPhone(String phone, String code);
}
