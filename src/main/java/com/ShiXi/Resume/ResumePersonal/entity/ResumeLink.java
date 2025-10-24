package com.ShiXi.Resume.ResumePersonal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("resume_link")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResumeLink {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 经历ID

    private Long resumeId; // 所属在线简历的ID

    private Long userId;

    private String resumeLink;

    private String attachmentName;

}
