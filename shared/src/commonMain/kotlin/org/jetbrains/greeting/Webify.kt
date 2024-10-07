package org.jetbrains.greeting


import org.jetbrains.greeting.responses.SpotifySearchResult
import org.jetbrains.greeting.responses.TrackAudioAnalysis


public class Webify : ApiClientInterface {

    internal lateinit var clientId: String
    internal lateinit var clientSecret: String

    public companion object {
        private lateinit var instance: Webify

        public fun getInstance(): Webify {
            if (!Companion::instance.isInitialized) {
                initInstance()
            }
            return instance
        }

        internal fun initInstance(): Webify {
            if (!Companion::instance.isInitialized) {
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

public fun Webify.setClientId(clientId: String): Webify {
    require(clientId.isNotEmpty()) { "Client ID cannot be empty" }
    this.clientId = clientId
    return this
}

public fun Webify.setClientSecret(clientSecret: String): Webify {
    require(clientSecret.isNotEmpty()) { "Client secret cannot be empty" }
    this.clientSecret = clientSecret
    return this
}
