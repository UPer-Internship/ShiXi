package com.ShiXi.team.service.impl;

import com.ShiXi.team.entity.TeamMember;
import com.ShiXi.common.mapper.TeamMemberMapper;
import com.ShiXi.team.service.TeamMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember> implements TeamMemberService {
}