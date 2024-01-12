package com.bgpark.opensearchspringboot.uk.company.service

import com.bgpark.opensearchspringboot.uk.company.domain.Company
import com.bgpark.opensearchspringboot.uk.company.domain.CompanyRepository
import com.bgpark.opensearchspringboot.uk.csv.dto.CsvCompanyVO
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
}