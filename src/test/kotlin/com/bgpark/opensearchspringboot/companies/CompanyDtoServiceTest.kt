package com.bgpark.opensearchspringboot.companies

import com.bgpark.opensearchspringboot.companies.es.CompanyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CompanyDtoServiceTest(
    @Mock val companyRepository: CompanyRepository
) {

    @Test
    fun `csv를 읽는다`() {
        val companyService = CompanyService(companyRepository)

        val companies = companyService.readOrganisationsFromCsv()

        assertThat(companies.size).isGreaterThan(1)
    }
}