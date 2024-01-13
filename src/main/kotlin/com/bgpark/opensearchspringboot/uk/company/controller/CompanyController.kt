package com.bgpark.opensearchspringboot.uk.company.controller

import com.bgpark.opensearchspringboot.uk.company.controller.dto.CompanySearchRequest
import com.bgpark.opensearchspringboot.uk.company.domain.Company
import com.bgpark.opensearchspringboot.uk.company.service.CompanyService
import com.bgpark.opensearchspringboot.uk.csv.service.CsvService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/uk/api/v1/companies")
class CompanyController(
    val csvService: CsvService,
    val companyService: CompanyService
) {

    @PostMapping
    fun saveCompanies(): ResponseEntity<Void> {
        val companies = csvService.readCompaniesFromCsv()
        companyService.saveAll(companies)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun findCompanies(
        companySearchRequest: CompanySearchRequest
    ): ResponseEntity<Page<Company>> {
        val companies = companyService.findCompanies(companySearchRequest)
        return ResponseEntity.ok(companies)
    }
}