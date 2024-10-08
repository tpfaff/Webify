package org.jetbrains.greeting

import io.ktor.client.engine.HttpClientEngineFactory
import okio.Path
import org.jetbrains.greeting.responses.Track

expect fun getApplicationDataDirectory(): Path
public expect fun httpClientEngine(): HttpClientEngineFactory<*>

