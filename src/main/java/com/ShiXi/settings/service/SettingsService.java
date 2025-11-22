package com.ShiXi.settings.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.settings.entity.Settings;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SettingsService extends IService<Settings> {
    /**
     * 获取用户隐私权限设置
     * @param userId
     * @return
     */
    Result getPrivacySettings(Long userId);

    /**
     * 获取用户设置信息
     * @return
     */
    Result getSettings();


    /**
     * 更新用户设置信息
     * @param settings
     * @return
     */
    Result updateSettings(Settings settings);
}
