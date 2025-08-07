package com.ShiXi.user.IdentityAuthentication.common.service.impl;

import com.ShiXi.common.mapper.CurrentIdentificationMapper;
import com.ShiXi.user.IdentityAuthentication.common.entity.CurrentIdentification;
import com.ShiXi.user.IdentityAuthentication.common.service.CurrentIdentificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrentIdentificationServiceImpl extends ServiceImpl<CurrentIdentificationMapper, CurrentIdentification> implements CurrentIdentificationService {
}
