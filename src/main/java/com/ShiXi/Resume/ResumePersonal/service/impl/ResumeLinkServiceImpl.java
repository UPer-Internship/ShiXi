package com.ShiXi.Resume.ResumePersonal.service.impl;

import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.ShiXi.Resume.ResumePersonal.entity.ResumeLink;
import com.ShiXi.Resume.ResumePersonal.service.OnlineResumeService;
import com.ShiXi.Resume.ResumePersonal.service.ResumeLinkService;
import com.ShiXi.common.mapper.ResumeExperienceMapper;
import com.ShiXi.common.mapper.ResumeLinkMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResumeLinkServiceImpl extends ServiceImpl<ResumeLinkMapper, ResumeLink> implements ResumeLinkService {
}
