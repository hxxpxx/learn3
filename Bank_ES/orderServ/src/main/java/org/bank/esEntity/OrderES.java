package org.bank.esEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "orderbean", shards = 5, replicas = 1)
public class OrderES {
    @Id
    private String orderNo;

    @Field(type = FieldType.Keyword)
    private String accountNo;

    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text)
    private String contacts;

    @Field(type = FieldType.Long)
    private Integer num;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Double)
    private Double totalAmout;

    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_max_word")
    private String phone;

    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_max_word")
    private String addressInfo;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Keyword)
    private String status;

    @Field(type = FieldType.Date,format = DateFormat.basic_date_time )
    private Date createtime;

    @Field(type = FieldType.Keyword )
    private  Long timestamp ;

    @Field(type = FieldType.Text)
    private String remark;


}