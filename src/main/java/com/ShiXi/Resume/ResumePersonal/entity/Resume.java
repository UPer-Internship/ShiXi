package com.ShiXi.Resume.ResumePersonal.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 简历中的经历类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resume_experience")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resume implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 经历ID

    private Long userId; // 所属在线简历的ID

    private String phone;// 手机号

    private String gender;

    private String birthDate;

    private String name;

    private String wechat;

    private String advantages;

    private String expectedPosition;

    //教育经历
    private String educationExperiences;
    //工作实习经历列表
    private String workAndInternshipExperiences;
    //项目经历列表
    private String projectExperiences;

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间

    private String resumeLink; // 附件链接
    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除
    // 工作实习经历内部类


}
