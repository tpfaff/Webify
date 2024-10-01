package org.jetbrains.greeting.responses

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)