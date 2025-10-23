package com.ShiXi.comment.domin.dto;

import com.ShiXi.common.domin.dto.BasicPageQueryReqDTO;
import lombok.Data;

@Data
public class pageQueryCommentReplyReqDTO extends BasicPageQueryReqDTO {
    private Long commentId;
}
