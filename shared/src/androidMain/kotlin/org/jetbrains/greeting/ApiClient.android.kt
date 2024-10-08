package org.jetbrains.greeting

internal actual fun initializeApiClient(clientId: String, clientSecret: String) {
    ApiClient.getInstance().init(clientId, clientSecret)
}
