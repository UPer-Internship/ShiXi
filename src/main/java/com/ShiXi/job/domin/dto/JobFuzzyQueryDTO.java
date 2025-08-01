package com.ShiXi.job.domin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模糊查询的查询类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobFuzzyQueryDTO {
    String keyWord;
    Integer page;
    Integer pageSize;
}
