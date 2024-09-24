package org.jetbrains.greeting

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform