package com.ShiXi.job.jobQuery.entity.es;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.InnerField;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Document(indexName = "prompt_words")
public class SearchHistory {

    @Id
    private String id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_max_analyzer"), // 注意：你的索引用的是ik_max_analyzer，不是ik_text_analyzer
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String word;

    // 关键修改：显式指定日期格式，兼容带T、空格分隔、时间戳三种格式
    @Field(
            type = FieldType.Date,
            format = DateFormat.custom, // 使用自定义格式
            pattern = "yyyy-MM-dd'T'HH:mm:ss||yyyy-MM-dd HH:mm:ss||epoch_millis" // 与ES索引格式完全对齐，同时兼容带T的格式
    )
    private LocalDateTime update_time;

    // 同update_time，保持格式一致
    @Field(
            type = FieldType.Date,
            format = DateFormat.custom,
            pattern = "yyyy-MM-dd'T'HH:mm:ss||yyyy-MM-dd HH:mm:ss||epoch_millis"
    )
    private LocalDateTime create_time;
}
