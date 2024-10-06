package org.jetbrains.greeting

//typealias TrackAudioAnalysisAndroid = TrackAudioAnalysis
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import org.jetbrains.greeting.responses.AccessToken
import org.jetbrains.greeting.responses.AccessTokenResponse
import org.jetbrains.greeting.responses.SpotifySearchResult
import org.jetbrains.greeting.responses.TrackAudioAnalysis
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


/***
 * Consider declaring computed properties and normal methods as extensions.
 * Only regular properties, overrides, and overloaded operators should be declared as members by default.
 */

//todo why out?
sealed class ApiResult<out T> {
    //how does output and input with <out> ?
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable) : ApiResult<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }
}

public class ApiClient private constructor() {
    private val baseUrl = "https://api.spotify.com/v1"
    private lateinit var client: HttpClient
    private lateinit var clientId: String
    private lateinit var clientSecret: String
    private lateinit var logLevel: LogLevel

    //todo builder that forces clientid and secret?

    public fun init(clientId: String, clientSecret: String, logLevel: LogLevel = LogLevel.ALL) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        this.logLevel = logLevel
        initHttpClient()
    }

    private fun initHttpClient() {
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        TokenProvider.instance.getToken()?.let {
                            BearerTokens(it.token, null)
                        }
                    }
                    refreshTokens {
                        getAuthToken(clientId, clientSecret).let {
                            TokenProvider.instance.saveToken(it)
                            BearerTokens(it.token, null)
                        }
                    }
                }
            }
            install(Logging) {
                level = logLevel
                logger = NapierLogger()
            }
        }
    }


    public companion object {
        private lateinit var instance: ApiClient
        public fun getInstance(): ApiClient {
            if (!::instance.isInitialized) {
                instance = ApiClient()
            }
            return instance
        }
    }


    /***
     * Gets an access token from spotify.
     * If an unexpired token exists locally, it will be used instead of requesting a new one
     */
    private suspend fun getAuthToken(
        clientId: String,
        clientSecret: String
    ): AccessToken {

        Napier.d("Getting new access token from spotify")
        val response = client.post("https://accounts.spotify.com/api/token") {
            // Set the Content-Type header
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                append(
                    HttpHeaders.Authorization,
                    "Basic ${encodeCredentials(clientId, clientSecret)}"
                )
            }

            // Set the form data in the request body
            setBody(FormDataContent(Parameters.build {
                append("grant_type", "client_credentials")
            }))
        }

        val tokenResponse = response.body<AccessTokenResponse>()

        // Use the typed response
        Napier.d("Access Token: ${tokenResponse.access_token}")
        Napier.d("Token Type: ${tokenResponse.token_type}")
        Napier.d("Expires In: ${tokenResponse.expires_in}")


        val token = AccessToken(
            tokenResponse.access_token,
            Clock.System.now().epochSeconds + tokenResponse.expires_in
        )
        return token
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeCredentials(clientId: String, clientSecret: String): String {
        val credentials = "$clientId:$clientSecret"
        val encodedString = Base64.encode(credentials.toByteArray())
        return encodedString
    }

//    //todo how does this return type work when ApiResult.Error is allowed to be returned?
//    public suspend fun searchForTrack(trackQuery: String): ApiResult<SpotifySearchResult> {
//        return try {
//            val result = client.get(baseUrl) {
//                parameter("q", "track: $trackQuery")
//                parameter("type", "track")
//            }
//
//            return result.toApiResult()
//        } catch (e: Exception) {
//            Napier.e("Error while searching for track", e)
//            ApiResult.Error(e)
//        }
//    }

    //todo how does this return type work when ApiResult.Error is allowed to be returned?
    public suspend fun searchForTrack(trackQuery: String): Result<SpotifySearchResult> {
        return try {
            val result = client.get("$baseUrl/search") {
                parameter("q", "track: $trackQuery")
                parameter("type", "track")
            }

            return result.toApiResult()
        } catch (e: Exception) {
            Napier.e("Error while searching for track", e)
            Result.failure(e)
        }
    }
    public suspend fun getTrackAnalysis(trackId: String): Result<TrackAudioAnalysis> {
        return client.get("$baseUrl/audio-analysis/$trackId").toApiResult()
    }
}

private suspend inline fun <reified T> HttpResponse.toApiResult(): Result<T> {
    if (this.status.value != 200) {
        Napier.e(this.bodyAsText())
        return Result.failure(Exception(this.bodyAsText()))
    }

    //reified because we need to know what the return type actually is at runtime, because it will map this.body()
    //to that type simply by us returning that type
    return Result.success(this.body())
}
//private suspend inline fun <reified T> HttpResponse.toApiResult(): ApiResult<T> {
//    if (this.status.value != 200) {
//        Napier.e(this.bodyAsText())
//        return ApiResult.Error(Exception(this.bodyAsText()))
//    }
//
//    //reified because we need to know what the return type actually is at runtime, because it will map this.body()
//    //to that type simply by us returning that type
//    return ApiResult.Success(this.body())
//}