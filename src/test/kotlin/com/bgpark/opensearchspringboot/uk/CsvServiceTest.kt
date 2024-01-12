package com.bgpark.opensearchspringboot.uk

import com.bgpark.opensearchspringboot.uk.csv.service.CsvService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CsvServiceTest(
    @Autowired val csvService: CsvService
) {

    @Test
    fun `csv를 es에 저장한다`() {
        val companies = csvService.readCompaniesFromCsv()
        csvService.save(companies)
    }
}