package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

@Serializable
data class MusicType(val type: String) {
    val TRACK = "track"
    val ALBUM = "album"
}