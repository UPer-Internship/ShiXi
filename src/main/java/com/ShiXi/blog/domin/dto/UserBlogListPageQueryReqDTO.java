package com.ShiXi.blog.domin.dto;

import lombok.Data;

@Data
public class UserBlogListPageQueryReqDTO  extends BasicPageQueryReqDTO{
    private Long userId;
}
