package com.ShiXi.user.IdentityAuthentication.studentTeamIdentification.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 学生团队身份认证实体类
 */
@Data
@Accessors(chain = true)
@TableName("student_team_identification")
public class StudentTeamIdentification implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;//直接关联的用户 id（负责人id）

    private String teamName;//团队名称

    private String universityName;//学校名称

    private String schoolName;//学院名称

    private String teamLeaderName;//团队负责人名称

    private Integer teamLeaderGender;//团队负责人性别 0 - 女 1 - 男

    private String address;//入驻基地的地址

    private String identificationImages;//团队身份认证图片

    @TableLogic
    private Integer isDeleted;//逻辑删除 0 - 未删除 1 - 已删除
}
