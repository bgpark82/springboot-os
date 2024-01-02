package com.bgpark.opensearchspringboot.companies

data class CompanyDto (
    val name: String,
    val townCity: String,
    val county: String?,
    val typeRating: String,
    val route: String
)