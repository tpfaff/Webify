package org.jetbrains.greeting

import io.github.aakira.napier.Napier
import io.ktor.util.logging.Logger

class NapierLogger: io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        Napier.d(message)
    }
}