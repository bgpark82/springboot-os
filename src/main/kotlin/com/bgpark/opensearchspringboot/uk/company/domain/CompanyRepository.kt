package com.bgpark.opensearchspringboot.uk.company.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.repository.CrudRepository

interface CompanyRepository: CrudRepository<Company, String> {

    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"should\": [\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"name\": \"?0\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"townCity\": \"?1\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"county\": \"?2\"\n" +
            "          }\n" +
            "        }\n" +
            "      ],\n" +
            "      \"filter\": [\n" +
            "        {\n" +
            "          \"term\": {\n" +
            "            \"typeRating.keyword\": \"?3\"\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"term\": {\n" +
            "            \"route.keyword\": \"?4\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n")
    fun findCompanies(
        name: String,
        townCity: String,
        county: String,
        typeRating: String,
        route: String,
        page: Pageable): Page<Company>
}