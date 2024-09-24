package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable


@Serializable
data class Artist(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)