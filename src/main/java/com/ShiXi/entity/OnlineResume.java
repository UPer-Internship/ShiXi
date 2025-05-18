package com.ShiXi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 在线简历实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("online_resume")
public class OnlineResume implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)

    private Long id; // ID

    private Long userId; // 学生ID, 外键

    private String name; // 姓名

    private String icon;//头像

    private String schoolName; // 学校名称

    private String major; // 专业名称

    private LocalDate graduationDate; // 毕业时间，格式：YYYY-MM-DD

    private Integer age; // 年龄

    private String phone; // 联系电话

    private String wechat; // 微信号

    private String educationLevel; // 学历（本科、大专、硕士、博士、其他）

    private String advantages; // 个人优势

    private LocalDateTime createTime; // 创建时间

    private LocalDateTime updateTime; // 更新时间
}
