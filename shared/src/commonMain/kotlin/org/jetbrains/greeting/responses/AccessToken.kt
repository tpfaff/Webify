package org.jetbrains.greeting.responses

import kotlinx.datetime.Clock

data class AccessToken(
    val token: String,
    val expiresAt: Long
){
    fun isExpired(): Boolean {
        return Clock.System.now().epochSeconds > expiresAt
    }
}