package com.amazingtlr.ktor

import app.cash.turbine.test
import com.amazingtlr.api.NetworkResult
import com.amazingtlr.api.user.models.UserProfileResponse
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

class KtorUserRepositoryObserveUserByUserLoginTest {
    private val mockEngine = MockEngine { request ->
        respond(
            content = """{
              "login": "mojombo",
              "id": "1",
              "avatar_url": "https://avatars.githubusercontent.com/u/1?v=4",
              "name": "Tom Preston-Werner",
              "public_repos": 66,
              "followers": 24052,
              "following": 11
              }""", // Mock JSON response
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
    fun `When fetching a specific user Then a a UserProfileResponse is send`() = runTest {
        sut.observeUserByUserLogin("mojombo").test {
            val item: NetworkResult<UserProfileResponse> = awaitItem()

            assert(item is NetworkResult.Success)
            with((item as NetworkResult.Success).data.user) {
                assert(id == "1")
                assert(login == "mojombo")
                assert(avatarUrl == "https://avatars.githubusercontent.com/u/1?v=4")
                assert(name == "Tom Preston-Werner")
                assert(nbOfRepo == 66)
                assert(followers == 24052)
                assert(following == 11)
            }
            awaitComplete()
        }
    }
}