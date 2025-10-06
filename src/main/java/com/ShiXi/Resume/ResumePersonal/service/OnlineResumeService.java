package com.ShiXi.Resume.ResumePersonal.service;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.Resume.ResumePersonal.domin.dto.UpdateResumeDTO;
import com.ShiXi.Resume.ResumePersonal.domin.dto.ResumePageQueryDTO;
import com.ShiXi.Resume.ResumePersonal.entity.Resume;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 根据用户ID查询简历（包含文字资料和附件OSS URL）
     * @param userId 用户ID
     * @return 简历信息
     */
    Result getResumeByUserId(Long userId);

    /**
     * 根据用户UUID查询简历（包含文字资料和附件OSS URL）
     * @param uuid 用户UUID
     * @return 简历信息
     */
    Result getResumeByUuid(String uuid);

    /**
     * 上传简历附件到OSS
     * @param file 简历附件文件
     * @return 上传结果，包含OSS URL
     */
    Result uploadResumeAttachment(MultipartFile file);

    /**
     * 查看简历附件OSS URL
     * @param resumeId 简历ID
     * @return 附件OSS URL
     */
    Result getResumeAttachmentUrl(Long resumeId);

    Result uploadResumeAttachmentV2(MultipartFile file);

    Result deleteAttachment(Long attachmentId);

    Result getAttachmentIds();

    Result getAttachmentById(Long attachmentId);
}
