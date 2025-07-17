package com.ShiXi.service;

import com.ShiXi.dto.Result;
import com.ShiXi.entity.ResumeExperience;
import com.ShiXi.entity.StudentInfo;
import com.ShiXi.vo.OnlineResumeVO;

public interface OnlineResumeService {
    /*
    * 保存在线简历
     */
     Result saveResumeInfo(StudentInfo studentInfo);

    /**
     * 添加经历
     */
     Result  saveExperienceInfo(ResumeExperience resumeExperience);

     /**
      * 修改在线简历
      */
     Result changeResumeInfo(StudentInfo studentInfo);

     /**
      * 修改经历
      */
     Result changeExperienceInfo(ResumeExperience resumeExperience);

    /*
    * 获取在线简历
     */
     Result getOnlineResume();

     OnlineResumeVO queryResumeById(Long id);
}
