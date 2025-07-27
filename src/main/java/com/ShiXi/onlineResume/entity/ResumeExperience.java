package com.ShiXi.onlineResume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class ResumeExperience implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 经历ID

    private Long studentInfoId; // 所属在线简历的ID

    private String type; // 经历类型（工作、实习、项目、作品集）

    private String description; // 经历描述，长文本

    private String link; // 作品的链接，允许为空

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间
    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除
}
