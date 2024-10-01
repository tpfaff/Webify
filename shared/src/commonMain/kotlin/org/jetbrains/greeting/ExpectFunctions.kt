package org.jetbrains.greeting

import okio.Path
import org.jetbrains.greeting.responses.Track

expect fun getApplicationDataDirectory(): Path
