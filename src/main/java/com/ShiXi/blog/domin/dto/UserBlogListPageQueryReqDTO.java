package com.ShiXi.blog.domin.dto;

import com.ShiXi.common.domin.dto.BasicPageQueryReqDTO;
import lombok.Data;

@Data
public class UserBlogListPageQueryReqDTO  extends BasicPageQueryReqDTO {
    private Long userId;
}
