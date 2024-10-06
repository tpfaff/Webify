package org.jetbrains.greeting

import org.jetbrains.greeting.responses.SpotifySearchResult
import org.jetbrains.greeting.responses.TrackAudioAnalysis

internal interface ApiClientInterface {
    suspend fun searchForTrack(trackQuery: String): Result<SpotifySearchResult>
    suspend fun getTrackAnalysis(trackId: String): Result<TrackAudioAnalysis>
}