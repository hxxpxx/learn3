package org.bank.esEntity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
/**
 * Spring Data通过注解来声明字段的映射属性，有下面的三个注解：
 * @Document 作用在类，标记实体类为文档对象，一般有四个属性
 * indexName：对应索引库名称
 * shards：分片数量，默认5
 * replicas：副本数量，默认1
 */
@Data
@Document(indexName = "account_detail_bean", shards = 5, replicas = 1)
public class AccountDetailES implements Serializable {
    /**
     * @Id 作用在成员变量，标记一个字段作为id主键
     */
    @Id
    private Integer id;
    @Field(type = FieldType.Keyword)
    private String accountNo;

    private String userName;

    @Field(type = FieldType.Keyword)
    private String userId;
    /**
     * @Field 作用在成员变量，标记为文档的字段，并指定字段映射属性：
     * type：字段类型，取值是枚举：FieldType
     * index：是否索引，布尔类型，默认是true
     * store：是否存储，布尔类型，默认是false
     * analyzer：分词器名称：ik_max_word
     */
    @Field(type = FieldType.Text,analyzer ="ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Double)
    private Double amount;

    @Field(type = FieldType.Double)
    private Double balance;

    @Field(type = FieldType.Keyword)
    private String orderNo;

    private Date createtime;

    @Field(type = FieldType.Keyword )
    private  Long timestamp ;


}