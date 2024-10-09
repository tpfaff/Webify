package org.jetbrains.greeting

internal interface WebifyBuilderBase<out T: WebifyBuilderBase<T>> {
    var clientId: String
    var clientSecret: String

    fun clientId(clientId: String): T
    fun clientSecret(clientSecret: String): T
}