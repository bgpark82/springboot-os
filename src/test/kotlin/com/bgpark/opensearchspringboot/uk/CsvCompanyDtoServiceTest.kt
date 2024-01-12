package com.bgpark.opensearchspringboot.uk

import com.bgpark.opensearchspringboot.uk.csv.service.CsvService
import com.bgpark.opensearchspringboot.uk.company.domain.CompanyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CsvCompanyDtoServiceTest(
    @Mock val companyRepository: CompanyRepository
) {

    @Test
    fun `csv를 읽는다`() {
        val csvService = CsvService(companyRepository)

        val companies = csvService.readCompaniesFromCsv()

        assertThat(companies.size).isGreaterThan(1)
    }
}