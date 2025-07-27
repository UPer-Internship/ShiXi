package com.ShiXi.company.entity;

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
@TableName("company")
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 主键ID
    private String name; // 企业名称
    private String logo; // 企业Logo图片地址
    private String industry; // 所属行业
    private String description; // 企业简介
    private String location; // 企业所在城市+区
    private String contactPerson; // 联系人
    private String contactPhone; // 联系电话
    private String contactEmail; // 联系邮箱
    private String website; // 企业官网
    private String scale; // 企业规模
    private String type; // 企业类型
    private Integer status; // 企业状态：0-禁用，1-正常，2-待审核
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间
    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
} 