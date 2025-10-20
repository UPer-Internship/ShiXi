package com.ShiXi.Resume.resumeBrowsingHistory.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.resumeBrowsingHistory.domain.dto.ResumeBrowsingHistoryDTO;
import com.ShiXi.Resume.resumeBrowsingHistory.entity.ResumeBrowsingHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 简历浏览记录业务层接口
 */
public interface ResumeBrowsingHistoryService extends IService<ResumeBrowsingHistory> {
    
    /**
     * 记录简历浏览
     * @param resumeBrowsingHistoryDTO 浏览记录信息
     * @return 记录结果
     */
    Result recordBrowsing(ResumeBrowsingHistoryDTO resumeBrowsingHistoryDTO);
    
    /**
     * 分页查询当前用户的浏览记录
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分页查询结果
     */
    Result pageQueryMyBrowsingHistory(Integer page, Integer pageSize);
    
    /**
     * 删除指定的浏览记录
     * @param id 浏览记录ID
     * @return 删除结果
     */
    Result deleteBrowsingHistory(Long id);
    
    /**
     * 清空当前用户的所有浏览记录
     * @return 清空结果
     */
    Result clearAllBrowsingHistory();
}