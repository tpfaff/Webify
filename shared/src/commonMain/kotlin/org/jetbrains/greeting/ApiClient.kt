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
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.toByteArray
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.greeting.responses.AccessToken
import org.jetbrains.greeting.responses.AccessTokenResponse
import org.jetbrains.greeting.responses.SpotifySearchResult
import org.jetbrains.greeting.responses.TrackAudioAnalysis
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


//todo
//inject header with token
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Throwable) : ApiResult<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }
}

class ApiClient private constructor() {
    private val baseUrl = "https://api.spotify.com/v1/search"
    private var token: String = ""
    lateinit var client: HttpClient
    private lateinit var clientId: String
    private lateinit var clientSecret: String

    //todo builder that forces clientid and secret?

    fun init(clientId: String, clientSecret: String) {
        this.clientId = clientId
        this.clientSecret = clientSecret
        initHttpClient()
    }

    private fun initHttpClient() {
        client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    // ... other Json configuration if needed
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
                level = LogLevel.ALL
                logger = NapierLogger()
            }
        }
    }


    companion object {
        private lateinit var instance: ApiClient
        fun getInstance(): ApiClient {
            if (!::instance.isInitialized) {
                instance = ApiClient()
            }
            return instance
        }
    }


    /***
     * Gets an access token from spotify
     * If one exists locally, it will be used instead of requesting a new one
     * Unless you force a network request by setting @useLocalTokenIfPresent to false
     */
    suspend fun getAuthToken(
        clientId: String,
        clientSecret: String,
        useLocalTokenIfPresent: Boolean = true
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

        //todo should the library save the token?
        //i think the api should be setup that
        //you request a token when you launch the app
        //if a valid token exists, use it...
        //else get a new one
        //and you should never have to worry about it again.

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

    suspend fun searchForTrack(trackQuery: String): ApiResult<SpotifySearchResult> {
        return try {
            val token = TokenProvider.instance.getToken()
            val result = client.get(baseUrl) {
                parameter("q", "track: $trackQuery")
                parameter("type", "track")
            }

            if (result.status.value != 200) {
                Napier.d(result.bodyAsText())
                return ApiResult.Error(Exception(result.bodyAsText()))
            } else {
                return ApiResult.Success(result.body())
            }

        } catch (e: Exception) {
            Napier.e("Error while searching for track", e)
            ApiResult.Error(e)
        }
    }

    suspend fun getTrackAnalysis(trackId: String): TrackAudioAnalysis {
        return client.get("$baseUrl/audio-analysis/$trackId").body()
    }

    suspend fun saveToken(token: AccessToken) {
        TokenProvider.instance.saveToken(token)
    }
}

@Serializable
data class Post(val userId: Int, val id: Int, val title: String, val body: String)