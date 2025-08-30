package com.ShiXi.common.service.impl;

import com.ShiXi.common.entity.Industry;
import com.ShiXi.common.mapper.IndustryMapper;
import com.ShiXi.common.service.IndustryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IndustryServiceImpl extends ServiceImpl<IndustryMapper, Industry> implements IndustryService {
}
