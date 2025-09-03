package com.ShiXi.job.jobQuery.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.domin.dto.EsJobQueryDTO;
import com.ShiXi.job.jobQuery.entity.es.EsJob;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class MysqlJobServiceImpl {

    // 直接注入，无需依赖Repository
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Result queryJob(EsJobQueryDTO esJobQueryDTO){
        // 创建一个BoolQueryBuilder用于构建动态查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        // 只有当keyword不为空时，才添加到查询条件中
        if (esJobQueryDTO.getTitle() != null && !esJobQueryDTO.getTitle().isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("title", esJobQueryDTO.getTitle()));
        }
        
        // 只有当salaryMin不为空时，才添加到查询条件中
        if (esJobQueryDTO.getSalaryMin() != null) {
            boolQuery.must(QueryBuilders.rangeQuery("salary_min").gte(esJobQueryDTO.getSalaryMin()));
        }
        
        // 只有当salaryMax不为空时，才添加到查询条件中
        if (esJobQueryDTO.getSalaryMax() != null) {
            boolQuery.must(QueryBuilders.rangeQuery("salary_max").lte(esJobQueryDTO.getSalaryMax()));
        }
        
        // 只有当financingProgress不为空时，才添加到查询条件中
        if (esJobQueryDTO.getFinancingProgress() != null && !esJobQueryDTO.getFinancingProgress().isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("financing_progress.keyword", esJobQueryDTO.getFinancingProgress()));
        }
        
        // 只有当enterpriseScale不为空时，才添加到查询条件中
        if (esJobQueryDTO.getEnterpriseScale() != null && !esJobQueryDTO.getEnterpriseScale().isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("enterprise_scale.keyword", esJobQueryDTO.getEnterpriseScale()));
        }
        
        // 只有当category不为空时，才添加到查询条件中
        if (esJobQueryDTO.getCategory() != null && !esJobQueryDTO.getCategory().isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("category.keyword", esJobQueryDTO.getCategory()));
        }
        
        // 只有当type不为空时，才添加到查询条件中
        if (esJobQueryDTO.getType() != null && !esJobQueryDTO.getType().isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("type.keyword", esJobQueryDTO.getType()));
        }


        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();
        return Result.ok(elasticsearchRestTemplate.search(searchQuery, EsJob.class));
    }


}
