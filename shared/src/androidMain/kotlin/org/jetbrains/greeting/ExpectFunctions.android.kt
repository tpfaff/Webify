package org.jetbrains.greeting

import okio.Path
import okio.Path.Companion.toPath

actual fun getApplicationDataDirectory(): Path {
    return ContextProvider.getInstance().getContext().filesDir.absolutePath.toPath()
}