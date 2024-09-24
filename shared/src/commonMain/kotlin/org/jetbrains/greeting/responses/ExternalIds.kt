package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

@Serializable
data class ExternalIds(
    val isrc: String
)