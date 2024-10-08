package org.jetbrains.greeting

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.*


//todo how to get ios source set?
actual fun getApplicationDataDirectory(): Path {
    val paths = NSSearchPathForDirectoriesInDomains(
        directory = NSDocumentDirectory,
        domainMask = NSUserDomainMask,
        expandTilde = true
    )
    val documentsDirectory = paths.firstObject as? String ?: ""
    return documentsDirectory.toPath()
}

public fun Webify.init(): Webify {
    Napier.base(DebugAntilog())
    ApiClient.getInstance()
        .init(clientId, clientSecret)

    return this
}

public actual fun httpClientEngine(): HttpClientEngineFactory<*> {
    return Darwin
}