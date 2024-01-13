package com.bgpark.opensearchspringboot.uk.company.service

import com.bgpark.opensearchspringboot.uk.company.controller.dto.CompanySearchRequest
import com.bgpark.opensearchspringboot.uk.company.domain.Company
import com.bgpark.opensearchspringboot.uk.company.domain.CompanyRepository
import com.bgpark.opensearchspringboot.uk.csv.dto.CsvCompanyVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CompanyService(
    val companyRepository: CompanyRepository
) {

    @Transactional
    fun saveAll(companies: List<CsvCompanyVO>): MutableIterable<Company> {
        return companyRepository.saveAll(companies.map { company ->
            Company(
                id = null,
                name = company.name,
                townCity = company.townCity,
                county = company.county,
                typeRating = company.typeRating,
                route = company.route
            )
        })
    }

    @Transactional(readOnly = true)
    fun findCompanies(request: CompanySearchRequest): Page<Company> {
        return companyRepository.findCompanies(
            name = request.name,
            townCity = request.townCity,
            county = request.county,
            typeRating = request.typeRating,
            route = request.route,
            page = PageRequest.of(request.page, request.size)
        )
    }
}