package com.ShiXi.Resume.ResumePersonal.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.domin.dto.ResumePageQueryDTO;
import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;

public interface OnlineResumeService extends IService<Resume> {
//    /*
//    * 保存在线简历
//     */
//     Result saveResumeInfo(StudentInfo studentInfo);
//
//    /**
//     * 添加经历
//     */
//     Result  saveExperienceInfo(ResumeExperience resumeExperience);
//
//     /**
//      * 修改在线简历
//      */
//     Result changeResumeInfo(StudentInfo studentInfo);
//
//     /**
//      * 修改经历
//      */
//     Result changeExperienceInfo(ResumeExperience resumeExperience);
//
//    /*
//    * 获取在线简历
//     */
//     Result getOnlineResume();
//
    Result getResumeByResumeId(Long id);

    Result getMyResume();

    Result updateMyExperience(UpdateResumeDTO reqDTO);

    /**
     * 分页查询简历公开信息
     * @param resumePageQueryDTO 查询条件
     * @return 分页查询结果
     */
    Result pageQueryPublicResumes(ResumePageQueryDTO resumePageQueryDTO);
}
