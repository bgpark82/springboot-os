package com.bgpark.opensearchspringboot.uk.company.domain

import org.springframework.data.repository.CrudRepository

interface CompanyRepository: CrudRepository<Company, String> {
}