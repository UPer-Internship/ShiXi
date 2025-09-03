package com.ShiXi.job.jobQuery.entity.es;

import lombok.Data;
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
@Document(indexName = "mysql_job_new")
public class EsJob {

    @Id
    private Long id; // 业务ID，作为文档ID

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String category;

    @Field(type = FieldType.Long)
    private Long company_id;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime create_time;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String enterprise_name;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String enterprise_scale;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String enterprise_type;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String financing_progress;

    @Field(type = FieldType.Boolean)
    private Boolean is_deleted;

    @Field(type = FieldType.Long)
    private Long publisher_id;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String salary_dimension;

    @Field(type = FieldType.Float)
    private Float salary_max;

    @Field(type = FieldType.Float)
    private Float salary_min;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String salary_round;

    @Field(type = FieldType.Boolean)
    private Boolean status;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String tag;

    @MultiField(
            mainField = @Field(type = FieldType.Text),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String tags;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String title;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String type;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private ZonedDateTime update_time;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String work_location_city;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String work_location_district;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "ik_text_analyzer"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String work_location_province;
}
