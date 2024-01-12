package com.bgpark.opensearchspringboot.uk.csv.service

import com.bgpark.opensearchspringboot.uk.company.domain.Company
import com.bgpark.opensearchspringboot.uk.company.domain.CompanyRepository
import com.bgpark.opensearchspringboot.uk.csv.dto.CsvCompanyDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.ResourceUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

@Service
class CsvService(
    val companyRepository: CompanyRepository
) {
    @Transactional
    fun save(companies: List<CsvCompanyDto>) {
        val companyList = companies.map { company ->
            Company(
                id = null,
                name = company.name,
                townCity = company.townCity,
                county = company.county,
                typeRating = company.typeRating,
                route = company.route
            )
        }
        companyRepository.saveAll(companyList)
    }

    fun readOrganisationsFromCsv(): List<CsvCompanyDto> {
        val companies = mutableListOf<CsvCompanyDto>()

        try {
            // CSV 파일 경로 설정
            val file = ResourceUtils.getFile("classpath:companies/companies.csv")

            // 파일 리더 생성
            BufferedReader(FileReader(file)).use { reader ->
                var line: String?
                // 헤더 스킵
                reader.readLine()

                // 각 라인을 읽어와서 객체로 변환 후 리스트에 추가
                while (reader.readLine().also { line = it } != null) {
                    val tokens = line!!.split(",")
                    val csvCompanyDto = CsvCompanyDto(
                        tokens[0].trim('"'),
                        tokens[1].trim('"'),
                        tokens[2].trim('"'),
                        tokens[3].trim('"'),
                        tokens[4].trim('"')
                    )
                    companies.add(csvCompanyDto)
                }
            }
        } catch (e: IOException) {
            // 예외 처리 - 파일을 읽는 도중 문제가 발생한 경우
            e.printStackTrace()
        }

        return companies
    }
}