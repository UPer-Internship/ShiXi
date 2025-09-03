package com.ShiXi.job.jobQuery.service.impl;

import com.ShiXi.common.domin.dto.Result;
import com.ShiXi.job.jobQuery.domin.dto.EsJobQueryDTO;
import com.ShiXi.job.jobQuery.entity.es.EsJob;
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
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                // 对title字段进行模糊匹配（会进行分词后匹配）
                .withQuery(QueryBuilders.matchQuery("title", esJobQueryDTO.getKeyWord()))
                // 如果需要精确匹配不分词的场景，可使用wildcardQuery（*表示任意字符）
                // .withQuery(QueryBuilders.wildcardQuery("title.keyword", "*" + keyword + "*"))
                .build();
        return Result.ok(elasticsearchRestTemplate.search(searchQuery, EsJob.class));
    }

    // 使用示例：新增文档
    public void save(EsJob job) {
        elasticsearchRestTemplate.save(job);
    }

    // 其他操作：查询、更新、删除等
    public EsJob findById(String id) {
        return elasticsearchRestTemplate.get(id, EsJob.class);
    }
}
