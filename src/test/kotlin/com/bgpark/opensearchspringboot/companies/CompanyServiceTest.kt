package com.bgpark.opensearchspringboot.companies

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CompanyServiceTest(
    @Autowired val companyService: CompanyService
) {

    @Test
    fun `csv를 es에 저장한다`() {
        val companies = companyService.readOrganisationsFromCsv()
        companyService.save(companies)
    }
}