package com.ShiXi.Resume.ResumePersonal.domin.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用于表示简历投递的VO类
 */
@Data
@EqualsAndHashCode
public class ReceiveResumeListVO {
    private Long jobId;//职位id
    private Long submitterId;//投递者id
    private String submitterName;//投递者姓名
    private String jobName;//职位名称
    private String gender;//性别
    private String icon;//头像
    private String schoolName;//学校名称
    private String major;//专业
    private String graduationDate;//毕业时间
    private String educationLevel;//学历水平
}
