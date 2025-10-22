package com.ShiXi.blog.ScheduledJob.domin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogScoreDTO {
    private Long id;
    private Integer likes;
    private LocalDateTime createTime;
}
