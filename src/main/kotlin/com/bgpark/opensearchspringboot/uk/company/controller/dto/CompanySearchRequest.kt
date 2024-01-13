package com.bgpark.opensearchspringboot.uk.company.controller.dto

data class CompanySearchRequest(
    var name: String,
    var townCity: String,
    var county: String,
    var typeRating: String,
    var route: String,
    var page: Int,
    var size: Int
)