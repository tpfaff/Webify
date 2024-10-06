package org.jetbrains.greeting

interface Platform {
    val name: String
}

public expect fun getPlatform(): Platform