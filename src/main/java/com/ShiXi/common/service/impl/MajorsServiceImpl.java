package com.ShiXi.common.service.impl;

import com.ShiXi.common.entity.Major;
import com.ShiXi.common.entity.Region;
import com.ShiXi.common.mapper.MajorMapper;
import com.ShiXi.common.mapper.RegionMapper;
import com.ShiXi.common.service.MajorsService;
import com.ShiXi.common.service.RegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MajorsServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorsService {
}
