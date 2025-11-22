package com.ShiXi.common.mapper;

import com.ShiXi.Resume.resumeBrowsingHistory.entity.ResumeBrowsingHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 简历浏览记录Mapper接口
 */
@Mapper
public interface ResumeBrowsingHistoryMapper extends BaseMapper<ResumeBrowsingHistory> {
}