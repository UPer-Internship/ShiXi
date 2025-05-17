package com.ShiXi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学生基本信息实体类
 */
@Data
@TableName("student_basic_info")
public class StudentBasicInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 学生姓名，例如：张三
     */
    private String name;

    /**
     * 性别，例如：男 / 女
     */
    private String gender;

    /**
     * 出生年月，格式为 yyyy/MM，例如：2003/10
     */
    private String birthDate;

    /**
     * 最高学历，例如：本科
     */
    private String highestEducation;

    /**
     * 学校名称，例如：华南理工大学
     */
    private String schoolName;

    /**
     * 专业名称，例如：计算机科学与技术
     */
    private String major;

    /**
     * 就读时间
     */
    private String studyPeriod;

    /**
     * 期望职位，例如：互联网-产品实习生
     */
    private String expectedPosition;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
