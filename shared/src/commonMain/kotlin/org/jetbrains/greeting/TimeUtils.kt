package org.jetbrains.greeting

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

internal class TimeUtils {
    fun getCurrentSystemTime(): Instant {
        return Clock.System.now()
    }

//    fun getCurrentTimePlusInterval(interval: Long): Instant {
//        return Clock.System.now().plus(Duration.)
//    }
}