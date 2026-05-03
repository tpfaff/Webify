package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

/**
 * Paginated list of artists from search results.
 */
@Serializable
public data class Artists(
    val href: String,
    val items: List<FullArtist>,
    val limit: Int,
    val next: String? = null,
    val offset: Int,
    val previous: String? = null,
    val total: Int
)
