package com.ShiXi.common.service.impl;

import com.ShiXi.common.entity.University;
import com.ShiXi.common.mapper.UniversityMapper;
import com.ShiXi.common.service.UniversityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UniversityServiceImpl extends ServiceImpl<UniversityMapper, University> implements UniversityService {
}