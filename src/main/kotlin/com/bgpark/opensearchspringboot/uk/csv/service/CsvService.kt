package com.bgpark.opensearchspringboot.uk.csv.service

import com.bgpark.opensearchspringboot.uk.common.CsvConstant.Companion.COMPANY_CSV_FILE_PATH
import com.bgpark.opensearchspringboot.uk.company.domain.CompanyRepository
import com.bgpark.opensearchspringboot.uk.csv.dto.CsvCompanyVO
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

@Service
class CsvService {

    fun readCompaniesFromCsv(): List<CsvCompanyVO> {
        val companies = mutableListOf<CsvCompanyVO>()

        try {
            // CSV 파일 경로 설정
            val file = ResourceUtils.getFile(COMPANY_CSV_FILE_PATH)

            // 파일 리더 생성
            BufferedReader(FileReader(file)).use { reader ->
                var line: String?
                // 헤더 스킵
                reader.readLine()

                // 각 라인을 읽어와서 객체로 변환 후 리스트에 추가
                while (reader.readLine().also { line = it } != null) {
                    val tokens = line!!.split(",")
                    val csvCompanyVO = CsvCompanyVO(
                        tokens[0].trim('"'),
                        tokens[1].trim('"'),
                        tokens[2].trim('"'),
                        tokens[3].trim('"'),
                        tokens[4].trim('"')
                    )
                    companies.add(csvCompanyVO)
                }
            }
        } catch (e: IOException) {
            // 예외 처리 - 파일을 읽는 도중 문제가 발생한 경우
            e.printStackTrace()
        }
        return companies
    }
}