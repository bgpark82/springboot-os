package com.bgpark.opensearchspringboot.spring_data_opensearch

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(
    val sampleRepository: SampleRepository
) {

    @Transactional
    fun save(sample: Sample): Sample {
        return sampleRepository.save(sample)
    }

    fun search(): MutableIterable<Sample> {
        return sampleRepository.findAll()
    }
}