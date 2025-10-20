package com.ShiXi.comment.domin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IsCommentLikeReqDTO {

    private Long commentId;
    private Long replyId;
}
