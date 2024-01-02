package com.bgpark.opensearchspringboot.companies.es

import org.springframework.data.repository.CrudRepository

interface CompanyRepository: CrudRepository<Company, String> {
}