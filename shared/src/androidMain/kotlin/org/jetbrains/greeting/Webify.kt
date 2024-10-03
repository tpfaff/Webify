package org.jetbrains.greeting

import android.app.Application
import android.content.Context
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.greeting.responses.AccessToken
import org.jetbrains.greeting.responses.SpotifySearchResult


class Webify : ApiClientInterface {

    private lateinit var clientId: String
    private lateinit var clientSecret: String

    companion object {
        private lateinit var instance: Webify


        //todo
        //study for interview the theory behind maybe
        //separating the clientid and clientsecret out
        //because you're thinking perhaps the person using the library
        //doesn't want to have to have injectable resources for this
        //i.e. in a place where you are init'ing with the context
        //i.e. likely to be in App or Activity etc
        fun init(context: Application, clientId: String, clientSecret: String): Webify {
            ContextProvider.getInstance().setContext(context)
            Napier.base(DebugAntilog())
            ApiClient
                .getInstance()
                .init(clientId, clientSecret)
            return getInstance().apply {
                this.clientId = clientId
                this.clientSecret = clientSecret
            }
        }

        private fun getInstance(): Webify {
            if (!::instance.isInitialized) {
                instance = Webify()
            }
            return instance
        }
    }

    override suspend fun searchForTrack(trackQuery: String): ApiResult<SpotifySearchResult> {
        return ApiClient.getInstance().searchForTrack(trackQuery)
    }

    fun enableNetworkLogging(enable: Boolean) {

    }

}