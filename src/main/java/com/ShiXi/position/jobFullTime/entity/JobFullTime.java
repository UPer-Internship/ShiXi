package com.ShiXi.position.jobFullTime.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job_full_time")
public class JobFullTime {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 自增id
    
    private Long publisherId; // 发布岗位者id
    
    private Long companyId; // 关联的公司id
    
    private String title; // 标题
    
    private BigDecimal salaryMin; // 薪水下限（单位K）
    
    private BigDecimal salaryMax; // 薪水上限（单位K）
    
    private String salaryRound; // 年发薪次数
    
    private String mainText; // 正文
    
    private String province; // 公司所在省份
    
    private String city; // 城市
    
    private String district; // 区县
    
    private String type; // 正职/兼职/实习
    
    private String tag; // 编略图tag列表 以json格式
    
    private Integer status; // 状态 0/1 课件/不可见
    
    @TableLogic
    private Integer isDeleted; // 逻辑删除 0/1 未删除/删除
    
    private String category; // 职位
    
    private String label; // 系统打标（暂时没用）
    
    private String financingProgress; // 融资进度
    
    private String enterpriseScale; // 企业规模
    
    private String industry; // 企业所在行业
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}