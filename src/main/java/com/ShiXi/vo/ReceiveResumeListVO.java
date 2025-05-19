package com.ShiXi.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@Data
@EqualsAndHashCode
public class ReceiveResumeListVO {
    private Long jobId;
    private Long submitterId;
    private String submitterName;
    private String jobName;
    private String gender;
    private String icon;
    private String schoolName;
    private String major;
    private LocalDate graduationDate;
    private String educationLevel;
}
