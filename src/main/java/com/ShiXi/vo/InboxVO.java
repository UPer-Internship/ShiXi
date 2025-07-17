package com.ShiXi.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用于封装与收件箱相关的数据
 */
@Data
@EqualsAndHashCode
public class InboxVO {
    private Long jobId;
    private Long submitterId;
//    private String submitterName;
//    private String jobName;
//    private String gender;
//    private String icon;
//    private String schoolName;
//    private String major;
//    private LocalDate graduationDate;
//    private String educationLevel;
}
