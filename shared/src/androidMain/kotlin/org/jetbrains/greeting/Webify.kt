package org.jetbrains.greeting

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.greeting.responses.SpotifySearchResult
import org.jetbrains.greeting.responses.TrackAudioAnalysis


public class Webify : ApiClientInterface {

    private lateinit var clientId: String
    private lateinit var clientSecret: String

    public companion object {
        private lateinit var instance: Webify


        //todo
        //study for interview the theory behind maybe
        //separating the clientid and clientsecret out
        //because you're thinking perhaps the person using the library
        //doesn't want to have to have injectable resources for this
        //i.e. in a place where you are init'ing with the context
        //i.e. likely to be in App or Activity etc
        public fun init(context: Application, clientId: String, clientSecret: String): Webify {

            require(clientId.isNotEmpty()) { "Client ID cannot be empty" }
            require(clientSecret.isNotEmpty()) { "Client secret cannot be empty" }

            ContextProvider.getInstance().setContext(context)
            Napier.base(DebugAntilog())

            ApiClient
                .getInstance()
                .init(clientId, clientSecret)

            return initInstance().apply {
                this.clientId = clientId
                this.clientSecret = clientSecret
            }
        }

        public fun getInstance(): Webify{
            if (!::instance.isInitialized) {
                throw IllegalStateException("Webify has not been initialized, call init() first")
            }
            return instance
        }

        private fun initInstance(): Webify {
            if (!::instance.isInitialized) {
                instance = Webify()
            }
            return instance
        }
    }

    override suspend fun searchForTrack(trackQuery: String): Result<SpotifySearchResult> {
        return ApiClient.getInstance().searchForTrack(trackQuery)
    }

    override suspend fun getTrackAnalysis(trackId: String): Result<TrackAudioAnalysis> {
        return ApiClient.getInstance().getTrackAnalysis(trackId)
    }
}

/**
 * Network logging is enabled by default
 */
public fun Webify.enableNetworkLogging(): Webify {

    return this
}

/**
 * Network logging is enabled by default
 */
public fun Webify.disableNetworkLogging(): Webify {

    return this
}