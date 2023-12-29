package com.bgpark.opensearchspringboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ElasticsearchDataAutoConfiguration::class] )
class OpensearchSpringbootApplication

fun main(args: Array<String>) {
    runApplication<OpensearchSpringbootApplication>(*args)
}
