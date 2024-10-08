package org.jetbrains.greeting

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO
import okio.Path
import okio.Path.Companion.toPath

actual fun getApplicationDataDirectory(): Path {
    return org.jetbrains.greeting.ContextProvider.getInstance().getContext().filesDir.absolutePath.toPath()
}

public actual fun httpClientEngine(): HttpClientEngineFactory<*> {
    return CIO
}