package org.jetbrains.greeting

import kotlin.random.Random

class Greeting {
    private val platform = getPlatform()
    fun greet(): String {
        val firstWord = if (Random.nextBoolean()) "HI!" else "Hello!"
        return "Hello, ${platform.name}!"
    }
}