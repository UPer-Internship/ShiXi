package com.ShiXi.position.common.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.position.common.domin.dto.JobBrowsingHistoryDTO;
import com.ShiXi.position.common.entity.JobBrowsingHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 岗位浏览记录业务层接口
 */
public interface JobBrowsingHistoryService extends IService<JobBrowsingHistory> {
    
    /**
     * 记录岗位浏览
     * @param jobBrowsingHistoryDTO 浏览记录信息
     * @return 记录结果
     */
    Result recordBrowsing(JobBrowsingHistoryDTO jobBrowsingHistoryDTO);
    
    /**
     * 分页查询当前用户的浏览记录
     * @param page 页码
     * @param pageSize 每页大小
     * @param positionType 岗位类型（可选，为空则查询所有类型）
     * @return 分页查询结果
     */
    Result pageQueryMyBrowsingHistory(Integer page, Integer pageSize, String positionType);
    
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