package com.ShiXi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student_info")
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID，外键关联 user 表
     */

    private Long userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，例如：男 / 女
     */
    private String gender;

    /**
     * 出生年月，格式为 yyyy/MM
     */
    private String birthDate;

    /**
     * 用户头像路径，默认空字符串
     */
    private String icon;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 专业名称
     */
    private String major;

    /**
     * 毕业时间，格式：YYYY-MM-DD
     */
    private LocalDate graduationDate;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 学历（本科、大专、硕士、博士、其他）
     */
    private String educationLevel;

    /**
     * 个人优势描述
     */
    private String advantages;

    /**
     * 期望职位
     */
    private String expectedPosition;

    /**
     * 创建时间，默认当前时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间，默认当前时间并随更新自动刷新
     */
    private LocalDateTime updateTime;
    @TableLogic
    private Integer isDeleted;// 逻辑删除标志，0-未删除，1-已删除
}
