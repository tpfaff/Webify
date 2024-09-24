package org.jetbrains.greeting

import org.jetbrains.greeting.responses.SpotifySearchResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import org.jetbrains.greeting.responses.TrackAudioAnalysis

class ApiClient {
    val baseUrl = "https://api.spotify.com/v1/search"
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }


    suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }

    suspend fun searchForTrack(trackQuery: String): SpotifySearchResult {
        return client.get(baseUrl) {
            parameter("q", "track: $trackQuery")
            parameter("type", "track")
        }.body()
    }

    suspend fun getTrackAnalysis(trackId: String): TrackAudioAnalysis {
        return client.get("$baseUrl/audio-analysis/$trackId").body()
    }

    fun getToken(): String {
        return "token"
    }
}

@Serializable
data class Post(val userId: Int, val id: Int, val title: String, val body: String)