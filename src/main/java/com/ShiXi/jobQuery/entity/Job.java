package com.ShiXi.jobQuery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long publisherId;
    private Long companyId; // 企业ID，逻辑外键
    private String title;
    private Double salaryMin; // 薪水下限
    private Double salaryMax; // 薪水上限
    private String frequency;//4天/周
    private String totalTime;//实习总时长
    private String onboardTime;//到岗时间（如一周内、一个月内等）
    private String enterpriseType;//外企 校友企业
    private String publisher;//hr名字
    private String enterpriseName;//腾讯 欧莱雅 阿里巴巴
    private String financingProgress;//A轮融资 D轮 不需要
    private String enterpriseScale;//10000人 100人
    private String workLocation;//珠海 横琴
    private String detailedInformation;//职位的详情信息
    private String category;//UI设计 游戏策划 ...
    private String type;//实习 兼职 课题
    private String tag;//岗位标签，如“远程-线下-可转正”
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除
    private Integer status; // 岗位状态：0-可申请，1-已截止
}
