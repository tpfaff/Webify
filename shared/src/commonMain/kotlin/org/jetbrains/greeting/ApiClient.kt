package org.jetbrains.greeting

import org.jetbrains.greeting.responses.SpotifySearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.greeting.responses.AccessTokenResponse
import org.jetbrains.greeting.responses.TrackAudioAnalysis
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
//typealias TrackAudioAnalysisAndroid = TrackAudioAnalysis


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
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                // ... other Json configuration if needed
            })
        }
        // ... other HttpClient configuration
    }

    companion object {
        val instance: ApiClient by lazy {
            ApiClient()
        }
    }

//    /***
//     * Gets an access token from spotify
//     * If one exists locally, it will be used instead of requesting a new one
//     * Unless you force a network request by setting @useLocalTokenIfPresent to false
//     */
//    suspend fun getAuthToken(clientId: String, clientSecret: String, useLocalTokenIfPresent: Boolean = true): String {
//        val response = client.post("https://accounts.spotify.com/api/token") {
//            // Set the Content-Type header
//            headers {
//                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
//                append(
//                    HttpHeaders.Authorization,
//                    "Basic ZDIwNjQ4Y2Q5ZTljNDc5YThkMWUxYzU1NWU2Y2ZmNzY6ZWUyMjVmMGE1YTEwNDRjZGIxZWYyYTk0YzhlMzFlNjA="
//                )
//            }
//
//            // Set the form data in the request body
//            setBody(FormDataContent(Parameters.build {
//                append("grant_type", "client_credentials")
//            }))
//        }
//
//        val tokenResponse = response.body<AccessTokenResponse>()
//
//        // Use the typed response
//        println("Access Token: ${tokenResponse.access_token}")
//        println("Token Type: ${tokenResponse.token_type}")
//        println("Expires In: ${tokenResponse.expires_in}")
//
//        //todo should the library save the token?
//        //i think the api should be setup that
//        //you request a token when you launch the app
//        //if a valid token exists, use it...
//        //else get a new one
//        //and you should never have to worry about it again.
//        return tokenResponse.access_token
//    }


    /***
     * Gets an access token from spotify
     * If one exists locally, it will be used instead of requesting a new one
     * Unless you force a network request by setting @useLocalTokenIfPresent to false
     */
    suspend fun getAuthToken(): String {
        val response = client.post("https://accounts.spotify.com/api/token") {
            // Set the Content-Type header
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                append(
                    HttpHeaders.Authorization,
                    "Basic WITHELD="
                )
            }

            // Set the form data in the request body
            setBody(FormDataContent(Parameters.build {
                append("grant_type", "client_credentials")
            }))
        }

        val tokenResponse = response.body<AccessTokenResponse>()

        // Use the typed response
        println("Access Token: ${tokenResponse.access_token}")
        println("Token Type: ${tokenResponse.token_type}")
        println("Expires In: ${tokenResponse.expires_in}")

        //todo should the library save the token?
        //i think the api should be setup that
        //you request a token when you launch the app
        //if a valid token exists, use it...
        //else get a new one
        //and you should never have to worry about it again.
        return tokenResponse.access_token
    }
    /**
     *
     */
//    private fun saveToken(authToken: String){
//        //todo how to save data agnostically?
//    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun encodeCredentials(clientId: String, clientSecret: String): String {
        val credentials = "$clientId:$clientSecret"
        val encodedString = Base64.encode(credentials.toByteArray())
        return encodedString
    }

    suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }

    suspend fun searchForTrack(trackQuery: String): ApiResult<SpotifySearchResult> {
        return try {
            val result = client.get(baseUrl) {
                headers {
                    append("Authorization", "Bearer ${getToken()}")
                }
                parameter("q", "track: $trackQuery")
                parameter("type", "track")
            }

            if(result.status.value != 200){
                println(result.bodyAsText())
                return ApiResult.Error(Exception(result.bodyAsText()))
            } else {
                return ApiResult.Success(result.body())
            }

        }catch (e: Exception){
            println(e.message)
            ApiResult.Error(e)
        }
    }

    suspend fun getTrackAnalysis(trackId: String): TrackAudioAnalysis {
        return client.get("$baseUrl/audio-analysis/$trackId").body()
    }

    fun setApiKey() {

    }

    private var token: String = ""
    fun saveToken(token: String){
        this.token = token
    }
    fun getToken(): String {
        return token
    }
}

@Serializable
data class Post(val userId: Int, val id: Int, val title: String, val body: String)