package org.jetbrains.greeting

import org.jetbrains.greeting.responses.SpotifySearchResult

interface ApiClientInterface {
    suspend fun searchForTrack(trackQuery: String): ApiResult<SpotifySearchResult>
    suspend fun getAuthToken(): String
    fun saveToken(token: String)
}