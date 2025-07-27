package com.ShiXi.onlineResume.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.onlineResume.entity.ResumeExperience;
import com.ShiXi.studentInfo.entity.StudentInfo;
import com.ShiXi.onlineResume.domin.vo.OnlineResumeVO;

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
