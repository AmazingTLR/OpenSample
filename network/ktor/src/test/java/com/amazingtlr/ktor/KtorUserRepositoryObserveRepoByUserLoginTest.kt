package com.amazingtlr.ktor

import app.cash.turbine.test
import com.amazingtlr.api.NetworkResult
import com.amazingtlr.api.repo.models.RepoListResponse
import com.amazingtlr.ktor.user.KtorUserRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test

class KtorUserRepositoryObserveRepoByUserLoginTest {
    private val mockEngine = MockEngine { request ->
        respond(
            content = """[{
            "id": "26899533",
            "name": "30daysoflaptops.github.io",
            "html_url": "https://github.com/mojombo/30daysoflaptops.github.io",
            "stargazers_count": 8,
            "language": "CSS",
            "description": "description"
            }]""", // Mock JSON response
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    }

    // Create a HttpClient with the mock engine
    private val client = HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(Json { var ignoreUnknownKeys = true })
        }
    }

    private val sut: KtorUserRepository = KtorUserRepository(client)

    @Test
    fun `When fetching a repo list for a  user Then a a RepoListResponse is send`() = runTest {
        sut.observeRepoByUserLogin(userLogin = "mojombo", repoPage = "1").test {
            val item: NetworkResult<RepoListResponse> = awaitItem()

            assert(item is NetworkResult.Success)
            with((item as NetworkResult.Success).data.repoList[0]) {
                assert(id == "26899533")
                assert(name == "30daysoflaptops.github.io")
                assert(repoUrl == "https://github.com/mojombo/30daysoflaptops.github.io")
                assert(stars == 8)
                assert(language == "CSS")
                assert(description == "description")
            }
            awaitComplete()
        }
    }
}