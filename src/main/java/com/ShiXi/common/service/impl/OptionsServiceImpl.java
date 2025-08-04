package com.ShiXi.common.service.impl;

import com.ShiXi.common.entity.Options;
import com.ShiXi.common.mapper.IdentificationMapper;
import com.ShiXi.common.mapper.OptionsMapper;
import com.ShiXi.common.service.OptionsService;
import com.ShiXi.user.IdentityAuthentication.common.entity.Identification;
import com.ShiXi.user.IdentityAuthentication.common.service.IdentificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OptionsServiceImpl extends ServiceImpl<OptionsMapper, Options> implements OptionsService {
}
