package com.ShiXi.settings;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.settings.entity.Settings;
import com.ShiXi.settings.service.SettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/settings")
@Tag(name = "用户设置相关接口")
public class SettingsController {
    @Resource
    private SettingsService settingsService;

    @GetMapping("/getSettings")
    @Operation(summary = "获取用户设置信息")
    public Result getSettings() {
        return settingsService.getSettings();
    }

    @PostMapping("/updateSettings")
    @Operation(summary = "更新用户设置信息")
    public Result updateSettings(Settings settings) {
        return settingsService.updateSettings(settings);
    }
}
