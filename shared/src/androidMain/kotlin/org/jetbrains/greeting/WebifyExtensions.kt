package org.jetbrains.greeting

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.greeting.Webify.Companion.initInstance

public fun Webify.init(context: Application): Webify {
    ContextProvider.getInstance().setContext(context)
    Napier.base(DebugAntilog())

    ApiClient.getInstance()
        .init(clientId, clientSecret)

    return this
}




