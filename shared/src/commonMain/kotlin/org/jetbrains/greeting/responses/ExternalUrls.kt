package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

@Serializable
data class ExternalUrls(
    val spotify: String
)