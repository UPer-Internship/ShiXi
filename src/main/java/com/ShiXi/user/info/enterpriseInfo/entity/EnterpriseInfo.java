package com.ShiXi.user.info.enterpriseInfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_enterprise_info")
public class EnterpriseInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 主键ID

    private Long userId; // 用户ID

    private String enterpriseName; // 企业名称

    private String yourPosition; // 你的职位

    private String enterpriseScale; // 企业规模

    private String enterpriseIndustry; // 企业所属行业

    private String enterpriseAddress; // 企业地址

    private String email; // 申请人邮箱

    @TableLogic
    private Integer isDeleted; // 逻辑删除标志，0-未删除，1-已删除
}
