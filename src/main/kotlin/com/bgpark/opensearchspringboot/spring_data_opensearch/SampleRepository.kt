package com.bgpark.opensearchspringboot.spring_data_opensearch

import org.springframework.data.repository.CrudRepository

interface SampleRepository: CrudRepository<Sample, String>