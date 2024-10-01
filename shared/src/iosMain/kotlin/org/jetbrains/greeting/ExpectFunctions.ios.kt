package org.jetbrains.greeting

import okio.Path
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