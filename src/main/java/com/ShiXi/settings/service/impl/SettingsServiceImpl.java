package com.ShiXi.settings.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.common.mapper.SettingsMapper;
import com.ShiXi.common.utils.UserHolder;
import com.ShiXi.settings.entity.Settings;
import com.ShiXi.settings.service.SettingsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Settings> implements SettingsService {
    @Override
    public Result getSettings() {
        Long userId = UserHolder.getUser().getId();

        // 根据用户ID查询设置信息
        Settings settings = this.lambdaQuery()
                .eq(Settings::getUserId, userId)
                .one();

        // 如果用户还没有设置信息，则创建默认设置
        if (settings == null) {
            settings = new Settings();
            settings.setUserId(userId);
            // 设置默认值：对陌生人友好
            settings.setPrivacyStranger(2);
            this.save(settings);
        }

        return Result.ok(settings);

    }

    @Override
    public Result getPrivacySettings(Long userId) {
        // 根据userId查询privateStranger字段
        Settings settings = this.lambdaQuery()
                .eq(Settings::getUserId, userId)
                .one();

        if (settings == null) {
            return Result.ok(2); //没有查到就返回0
        }

        return Result.ok(settings.getPrivacyStranger());
    }

    @Override
    public Result updateSettings(Settings settings) {
        Long userId = UserHolder.getUser().getId();

        // 验证参数
        if (settings == null) {
            return Result.fail("设置信息不能为空");
        }

        // 检查隐私设置值是否合法
        Integer privacyStranger = settings.getPrivacyStranger();
        if (privacyStranger != null && (privacyStranger < 0 || privacyStranger > 2)) {
            return Result.fail("隐私设置值不合法，应为0、1或2");
        }

        // 查询用户是否已有设置记录
        Settings existingSettings = this.lambdaQuery()
                .eq(Settings::getUserId, userId)
                .one();

        try {
            if (existingSettings == null) {
                // 如果没有设置记录，则创建新记录
                settings.setUserId(userId);
                this.save(settings);
            } else {
                // 如果已有记录，则更新记录
                existingSettings.setPrivacyStranger(settings.getPrivacyStranger());
                this.updateById(existingSettings);
            }

            return Result.ok("设置更新成功");
        } catch (Exception e) {
            log.error("更新用户设置失败，用户ID: {}", userId, e);
            return Result.fail("设置更新失败");
        }
    }
}
