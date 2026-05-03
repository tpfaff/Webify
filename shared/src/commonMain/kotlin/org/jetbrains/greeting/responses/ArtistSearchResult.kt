package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

/**
 * Response from Spotify search API when searching for artists.
 */
@Serializable
public data class ArtistSearchResult(
    val artists: Artists
)
