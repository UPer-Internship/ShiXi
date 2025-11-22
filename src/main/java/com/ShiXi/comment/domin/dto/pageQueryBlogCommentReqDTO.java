package com.ShiXi.comment.domin.dto;

import com.ShiXi.common.domin.dto.BasicPageQueryReqDTO;
import lombok.Data;

@Data
public class pageQueryBlogCommentReqDTO extends BasicPageQueryReqDTO {
    private Long blogId;
}
