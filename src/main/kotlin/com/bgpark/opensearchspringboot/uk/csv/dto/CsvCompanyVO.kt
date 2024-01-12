package com.bgpark.opensearchspringboot.uk.csv.dto

data class CsvCompanyVO (
    val name: String,
    val townCity: String,
    val county: String?,
    val typeRating: String,
    val route: String
)