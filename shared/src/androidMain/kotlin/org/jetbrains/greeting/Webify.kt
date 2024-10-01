package org.jetbrains.greeting

import android.app.Application
import android.content.Context
import org.jetbrains.greeting.responses.SpotifySearchResult


class Webify : ApiClientInterface {

    companion object {
        private lateinit var instance: Webify

        fun init(context: Application): Webify {
            ContextProvider.getInstance().setContext(context)
            return getInstance()
        }

        private fun getInstance(): Webify {
            if(!::instance.isInitialized) {
                instance = Webify()
            }
            return instance
        }
    }

    override suspend fun searchForTrack(trackQuery: String): ApiResult<SpotifySearchResult> {
        return ApiClient.instance.searchForTrack(trackQuery)
    }

    override suspend fun getAuthToken(): String {
        return ApiClient.instance.getAuthToken()
    }

    override fun saveToken(token: String) {
        ApiClient.instance.saveToken(token)
    }


}