package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

@Serializable
data class SpotifySearchResult(
    val tracks: Tracks
)