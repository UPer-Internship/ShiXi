package com.ShiXi.blog.ScheduledJob.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public  class BlogScoreUpdateDTO {
    private Long id;
    private Double score;
}
