package com.bgpark.opensearchspringboot.java_client

import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.opensearch.client.RestClient
import org.opensearch.client.json.jackson.JacksonJsonpMapper
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch.core.DeleteRequest
import org.opensearch.client.opensearch.core.DeleteResponse
import org.opensearch.client.opensearch.core.IndexRequest
import org.opensearch.client.opensearch.core.SearchRequest
import org.opensearch.client.opensearch.core.SearchResponse
import org.opensearch.client.opensearch.indices.CreateIndexRequest
import org.opensearch.client.opensearch.indices.DeleteIndexRequest
import org.opensearch.client.opensearch.indices.DeleteIndexResponse
import org.opensearch.client.opensearch.indices.IndexSettings
import org.opensearch.client.opensearch.indices.PutIndicesSettingsRequest
import org.opensearch.client.transport.rest_client.RestClientTransport
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension


private const val OS_USERNAME = "musinsa"
private const val OS_PASSWORD = "Musinsa1!2@"
private const val OS_HOSTNAME = "search-movies-5jmf7lgf2emqczejio7xw4bgnu.aos.ap-northeast-2.on.aws"
private const val OS_PORT = 443
private const val OS_SCHEMA = "https"

private const val INDEX = "sample"

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class JavaClientTest {

    var client: OpenSearchClient? = null

    @BeforeEach
    fun setUp() {
        val httpHost = HttpHost(OS_HOSTNAME, OS_PORT, OS_SCHEMA)
        val credentials = UsernamePasswordCredentials(OS_USERNAME, OS_PASSWORD)

        val credentialsProvider = basicCredentialsProvider(httpHost, credentials)

        val restClient = restClient(httpHost, credentialsProvider)

        val transport = RestClientTransport(restClient, JacksonJsonpMapper())
        client = OpenSearchClient(transport)
    }

    @Test
    fun `인덱스 생성`() {
        // create index
        val request = CreateIndexRequest.Builder()
            .index(INDEX)
            .build()

        val create = client?.indices()?.create(request)

        val settings = IndexSettings.Builder()
            .autoExpandReplicas("0-all") // 샤드 수 자동 확장 설정 (0에서 all)
            .build()

        val settingsRequest = PutIndicesSettingsRequest.Builder()
            .index(INDEX)
            .settings(settings)
            .build()

        val putSettings = client?.indices()?.putSettings(settingsRequest)

        assertThat(create?.acknowledged()).isTrue()
        assertThat(putSettings?.acknowledged()).isTrue()
    }

    @Test
    fun `도큐먼트 추가`() {
        // indexing data
        val indexRequest: IndexRequest<Sample> = IndexRequest.Builder<Sample>()
            .index(INDEX)
            .id("1")
            .document(Sample("peter", "park"))
            .build()

        val response = client?.index(indexRequest)

        assertAll(
            { assertThat(response?.id()).isEqualTo("1") },
            { assertThat(response?.index()).isEqualTo(INDEX) },
            { assertThat(response?.result()).isEqualTo("Updated") },
        )
    }

    @Test
    fun `도큐먼트 조회`() {
        val request = SearchRequest.Builder()
            .index(INDEX)
            .build()

        val searchResponse: SearchResponse<Sample>? = client?.search(request, Sample::class.java)

        for (i in 0 until searchResponse?.hits()?.hits()?.size!!) {
            assertThat(searchResponse?.hits()?.hits()!![i].source()?.lastName).isEqualTo("park")
            assertThat(searchResponse?.hits()?.hits()!![i].source()?.firstName).isEqualTo("peter")
        }
    }

    @Test
    fun `도큐먼트 삭제`() {
        val request = DeleteRequest.Builder()
            .index(INDEX)
            .id("1")
            .build()

        val searchResponse: DeleteResponse? = client?.delete(request)

        assertThat(searchResponse?.index()).isEqualTo(INDEX)
        assertThat(searchResponse?.id()).isEqualTo("1")
    }

    @Test
    fun `인덱스 삭제`() {
        val request = DeleteIndexRequest.Builder()
            .index(INDEX)
            .build()

        val searchResponse: DeleteIndexResponse? = client?.indices()?.delete(request)

        assertThat(searchResponse?.acknowledged()).isTrue()
    }

    private fun basicCredentialsProvider(
        httpHost: HttpHost,
        credentials: UsernamePasswordCredentials
    ): BasicCredentialsProvider {
        val credentialsProvider = BasicCredentialsProvider()

        credentialsProvider.setCredentials(AuthScope(httpHost), credentials)

        return credentialsProvider
    }

    private fun restClient(
        httpHost: HttpHost,
        credentialsProvider: BasicCredentialsProvider
    ): RestClient? = RestClient.builder(httpHost)
        .setHttpClientConfigCallback { httpClientBuilder ->
            httpClientBuilder.setDefaultCredentialsProvider(
                credentialsProvider
            )
        }.build()
}