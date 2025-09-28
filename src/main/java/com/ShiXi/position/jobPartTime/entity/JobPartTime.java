package com.ShiXi.position.jobPartTime.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job_part_time")
public class JobPartTime {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 自增id

    private Long publisherId; // 发布岗位者id

    private Long companyId; // 关联的公司id

    private String title; // 标题

    private Double salaryMin; // 薪水下限（单位元）

    private Double salaryMax; // 薪水上限（单位元）

    private String mainText; // 正文

    private String province; // 公司所在省份

    private String city; // 城市

    private String district; // 区县

    private String type; // 正职/兼职/实习

    @TableField(typeHandler = FastjsonTypeHandler.class)
    @JSONField
    private List<String> tag; // 标签列表，使用FastJSON序列化存储

    private Integer status; // 状态 0/1 不可见/可见

    @TableLogic
    private Integer isDeleted; // 逻辑删除 0/1 未删除/删除

    private String category; // 职位

    private String label; // 系统打标

    private String financingProgress; // 融资进度

    private String enterpriseScale; // 企业规模

    private String industry; // 企业所在行业
    
    private String experienceRequirement; // 经验要求
    
    private String educationRequirement; // 学历要求

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}