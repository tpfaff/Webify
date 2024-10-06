package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

@Serializable
public data class SpotifySearchResult(
    val tracks: Tracks
)