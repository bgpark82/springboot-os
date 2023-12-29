package com.bgpark.opensearchspringboot.spring_data_opensearch

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "sample")
data class Sample(

    @Id
    var id: String?,

    @Field(type = FieldType.Text, name = "firstName")
    var firstName: String? = "",

    @Field(type = FieldType.Text, name = "lastName")
    var lastName: String? = ""
)