package com.bgpark.opensearchspringboot.uk.company.domain

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "uk")
data class Company(
    @Id
    val id: String? = null,
    val name: String,
    val townCity: String,
    val county: String?,
    val typeRating: String,
    val route: String
)