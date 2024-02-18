package com.bgpark.opensearchspringboot.java_client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.apache.hc.core5.http.HttpHost
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.opensearch.client.opensearch.OpenSearchClient
import org.opensearch.client.opensearch._types.Result
import org.opensearch.client.opensearch._types.SortOrder
import org.opensearch.client.opensearch.core.DeleteRequest
import org.opensearch.client.opensearch.core.IndexRequest
import org.opensearch.client.opensearch.core.SearchRequest
import org.opensearch.client.opensearch.indices.CreateIndexRequest
import org.opensearch.client.opensearch.indices.DeleteIndexRequest
import org.opensearch.client.opensearch.indices.IndexSettings
import org.opensearch.client.opensearch.indices.PutIndicesSettingsRequest
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JavaClientServiceTest {

    lateinit var openSearchClient: OpenSearchClient
    val index = "sample-index"

    @BeforeEach
    fun setUp() {
        val host = HttpHost("http", "localhost", 9200)
        val hosts = arrayOf(host)

        val transport = ApacheHttpClient5TransportBuilder
            .builder(*hosts)
            .build()

        openSearchClient = OpenSearchClient(transport)
    }

//    @AfterEach
//    fun tearDown() {
//        val deleteRequest = DeleteIndexRequest.Builder()
//            .index(index)
//            .build()
//
//        openSearchClient.indices()
//            .delete(deleteRequest)
//    }

    @Test
    fun `인덱스를 생성한다`() {
        val createIndexRequest = CreateIndexRequest.Builder()
            .index(index)
            .build()

        val create = openSearchClient.indices().create(createIndexRequest)

        assertThat(create.acknowledged()).isTrue()
    }

    @Test
    fun `인덱스를 생성한다 - settings`() {
        val indexSettings = IndexSettings.Builder()
            .autoExpandReplicas("0-all")
            .build()

        val putIndicesSettingsRequest = PutIndicesSettingsRequest.Builder()
            .index(index)
            .settings(indexSettings)
            .build()

        val putSettings =
            openSearchClient.indices().putSettings(putIndicesSettingsRequest)

        assertThat(putSettings.acknowledged()).isTrue()
    }

    @Test
    fun `인덱스를 삭제한다`() {
        val deleteRequest = DeleteIndexRequest.Builder()
            .index(index)
            .build()

        val response = openSearchClient.indices().delete(deleteRequest)

        assertAll(
            { assertThat(response.acknowledged()).isTrue() },
        )
    }

    @Test
    fun `도큐먼트를 생성한다`() {
        val indexRequest = IndexRequest.Builder<Sample>()
            .index(index)
            .id("1")
            .document(Sample("peter", "park"))
            .build()

        val response = openSearchClient.index(indexRequest)

        assertAll(
            { assertThat(response.id()).isEqualTo("1") },
            { assertThat(response.index()).isEqualTo(index) },
            { assertThat(response.result()).isEqualTo(Result.Created) },
            // { assertThat(response.version()).isEqualTo(7) }, 도큐먼트를 추가, 수정, 삭제할 떄마다 버전이 증가한다
        )
    }

    @Test
    fun `도큐먼트를 조회한다`() {
        val searchRequest = SearchRequest.Builder()
            .index(index)
            .build()

        val response = openSearchClient.search(searchRequest, Sample::class.java)

        for (sample in response.hits().hits()) {
            assertThat(sample.source()?.firstName).isEqualTo("peter")
        }

    }

    @Test
    fun `도큐먼트를 조회한다 - map`() {
        val searchRequest = SearchRequest.Builder()
            .index(index)
            .from(0)
            .size(10)
            .query{ q -> q.bool {
                bool -> bool.filter {
                    filter -> filter.term {
                        term -> term.field("firstName").value {
                            value -> value.stringValue("peter")
                        }
                    }
                }
            }}
            .source { s -> s.filter {
                filter -> filter.includes(listOf("firstName", "lastName"))
            }}
            .sort { s -> s.field {
                field -> field.order(SortOrder.Asc)
            }}
            .build()

        val response = openSearchClient.search(
            searchRequest,
            ObjectNode::class.java)

        val hits = response.hits().hits()
        for (hit in hits) {
            val objectNode = hit.source()
            val map = ObjectMapper().convertValue(objectNode, Map::class.java) as Map<String, Any> }
    }

    @Test
    fun `도큐먼트를 삭제한다`() {
        val deleteRequest = DeleteRequest.Builder()
            .index(index)
            .id("1")
            .build()

        val response = openSearchClient.delete(deleteRequest)

        assertAll(
            { assertThat(response.id()).isEqualTo("1") },
            { assertThat(response.index()).isEqualTo(index) },
        )
    }
}