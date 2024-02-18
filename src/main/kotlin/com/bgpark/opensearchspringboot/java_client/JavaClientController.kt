package com.bgpark.opensearchspringboot.java_client

import org.apache.hc.core5.http.HttpHost
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch.core.SearchRequest
import org.opensearch.client.opensearch.core.SearchResponse
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JavaClientController {

    @GetMapping("/java-client")
    fun findAll(): SearchResponse<Sample>? {
        val index = "sample-index"

        val host = HttpHost("http", "localhost", 9200)
        val hosts = arrayOf(host)

        val transport = ApacheHttpClient5TransportBuilder
            .builder(*hosts)
            .build()

        val openSearchClient = OpenSearchClient(transport)

        val searchRequest = SearchRequest.Builder()
            .index(index)
            .build()

        val response = openSearchClient.search(searchRequest, Sample::class.java)

        return response
    }
}