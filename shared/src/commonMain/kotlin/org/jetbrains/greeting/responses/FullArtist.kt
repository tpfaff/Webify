package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

/**
 * Full artist object returned from artist search.
 * Contains more details than the simplified Artist object embedded in tracks.
 */
@Serializable
public data class FullArtist(
    val external_urls: ExternalUrls,
    val followers: Followers? = null,
    val genres: List<String> = emptyList(),
    val href: String,
    val id: String,
    val images: List<Image> = emptyList(),
    val name: String,
    val popularity: Int = 0,
    val type: String,
    val uri: String
)

@Serializable
public data class Followers(
    val href: String? = null,
    val total: Int
)
