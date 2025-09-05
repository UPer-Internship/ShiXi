package com.ShiXi.job.jobQuery.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.domin.dto.EsJobQueryDTO;
import com.ShiXi.job.jobQuery.entity.es.EsJob;
import com.ShiXi.job.jobQuery.entity.es.SearchHistory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class MysqlJobServiceImpl {

    // 直接注入，无需依赖Repository
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Result saveSearchHistory(String keyword) {
        BoolQueryBuilder searchHistoryQuery = QueryBuilders.boolQuery();
        searchHistoryQuery.must(QueryBuilders.matchQuery("word.keyword", keyword));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(searchHistoryQuery)
                .build();
        return Result.ok( elasticsearchRestTemplate.search(searchQuery, SearchHistory.class));
    }

    public Result queryJob(EsJobQueryDTO esJobQueryDTO){
        // 创建一个BoolQueryBuilder用于构建动态查询条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        
        // 只有当keyword不为空时，才添加到查询条件中
        if (esJobQueryDTO.getTitle() != null && !esJobQueryDTO.getTitle().isEmpty()) {
            boolQuery.must(QueryBuilders.matchQuery("title", esJobQueryDTO.getTitle()));

            BoolQueryBuilder searchHistoryQuery = QueryBuilders.boolQuery();
            searchHistoryQuery.must(QueryBuilders.matchQuery("word.keyword", esJobQueryDTO.getTitle()));
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(searchHistoryQuery)
                    .build();
            SearchHits<SearchHistory> searchHits= elasticsearchRestTemplate.search(searchQuery, SearchHistory.class);
            SearchHistory searchHistory=new SearchHistory();
            if (!searchHits.isEmpty()) {
                    // 如果已存在，则更新第一个匹配的记录
                    searchHistory = searchHits.getSearchHit(0).getContent();
                } else {
                    // 如果不存在，则创建新记录
                    searchHistory.setWord(esJobQueryDTO.getTitle());
                    searchHistory.setCreate_time(LocalDateTime.now());
                }
                searchHistory.setUpdate_time(LocalDateTime.now());
                elasticsearchRestTemplate.save(searchHistory);
//            try {
//                // 将用户的搜索关键词存入到es中的另一个索引当中
//                // 先检查是否已存在相同的title
//                BoolQueryBuilder searchHistoryQuery = QueryBuilders.boolQuery();
//                searchHistoryQuery.must(QueryBuilders.matchQuery("word.keyword", esJobQueryDTO.getTitle()));
//
//                NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                        .withQuery(searchHistoryQuery)
//                        .build();
//
//                SearchHits<SearchHistory> searchHits = elasticsearchRestTemplate.search(searchQuery, SearchHistory.class);
//
//                SearchHistory searchHistory;
//                if (!searchHits.isEmpty()) {
//                    // 如果已存在，则更新第一个匹配的记录
//                    searchHistory = searchHits.getSearchHit(0).getContent();
//                } else {
//                    // 如果不存在，则创建新记录
//                    searchHistory = new SearchHistory(esJobQueryDTO.getTitle());
//                }
//                // 更新时间字段
//                searchHistory.setUpdate_time(new Date());
//                elasticsearchRestTemplate.save(searchHistory);
//            } catch (Exception e) {
//                // 如果出现日期解析等错误，创建一个新的记录
//                try {
//                    SearchHistory searchHistory = new SearchHistory(esJobQueryDTO.getTitle());
//                    searchHistory.setUpdate_time(new Date());
//                    elasticsearchRestTemplate.save(searchHistory);
//                } catch (Exception innerException) {
//                    // 即使保存搜索历史失败，也不应该影响主要的搜索功能
//                    // 可以在这里记录日志
//                }
//            }
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
