package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable
import org.jetbrains.greeting.responses.Album
import org.jetbrains.greeting.responses.Artist
import org.jetbrains.greeting.responses.ExternalIds
import org.jetbrains.greeting.responses.ExternalUrls

@Serializable
data class Item(
    val album: Album,
    val artists: List<Artist>,
    val available_markets: List<String>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ExternalIds,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: String?,
    val track_number: Int,
    val type: String,
    val uri: String
)