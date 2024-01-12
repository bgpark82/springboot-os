package com.bgpark.opensearchspringboot.uk.company.controller

import com.bgpark.opensearchspringboot.uk.company.service.CompanyService
import com.bgpark.opensearchspringboot.uk.csv.service.CsvService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uk/api/v1")
class CompanyController(
    val csvService: CsvService,
    val companyService: CompanyService
) {

    @PostMapping("/companies")
    fun saveCompanies(): ResponseEntity<Void> {
        val companies = csvService.readCompaniesFromCsv()
        companyService.saveAll(companies)
        return ResponseEntity.ok().build()
    }
}