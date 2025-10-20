package com.ShiXi.comment.domin.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentVO {
    private Long id;

    private Long userId;

    private List<Long> mentionIds;

    private Long blogId;

    private String image;

    private String content;

    private Integer likeCount = 0;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
