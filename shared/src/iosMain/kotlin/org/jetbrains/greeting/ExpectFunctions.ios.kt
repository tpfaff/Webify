package org.jetbrains.greeting

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import okio.Path
import org.jetbrains.greeting.Webify.Companion.initInstance
import org.jetbrains.greeting.Webify.Companion.instance
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

