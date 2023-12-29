package com.bgpark.opensearchspringboot.spring_data_opensearch

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * This class contains test methods for Spring Data OpenSearch integration.
 *
 * It is annotated with @SpringBootTest, which indicates that this is a Spring Boot test class.
 */
@SpringBootTest
class SpringDataOpenSearchTest(
    @Autowired
    val sampleService: SampleService
) {

    @Test
    fun `도큐먼트를 저장한다`() {
        val save = sampleService.save(Sample(null, "peter", "park"))

        Assertions.assertThat(save.firstName).isEqualTo("peter")
        Assertions.assertThat(save.lastName).isEqualTo("park")
    }

    @Test
    fun `도큐먼트를 조회한다`() {
        val search = sampleService.search()

        for (sample in search) {
            println(sample)
        }
    }
}