package org.jetbrains.greeting


/***
 * This class exists to enforce some required functions and properties cross platform
 * for the Webify's builder.
 * There is diverging implementations of it because android requires a Context object to be passed in.
 * To be able to call a chain of builder methods, a WebifyBuilder must be returned, instead of a WebifyBuilderBase.
 * Because this supertype doesn't contain a context, so the chain that calls setContext on android won't work.
 * If the base type is returned.
 * Basically, using covariance here, allows us to override these functions (allows us to enforce an interface on a class)
 * While also returning the subtype from the function (changing the signature of the function that is required by the supertype)
 * in place of the supertype, so that platform specific subtype implementations
 * Such as setting context, can be chained together.
 *
 */

/**
 * AI comment
 *
 * This interface defines the common properties and methods for the `Webify` builder across platforms.
 * It uses covariance (`out`) to allow platform-specific implementations to return their own type,
 * enabling chaining of methods, including platform-specific ones like `context()` on Android.
 */
internal interface WebifyBuilderBase<out T: WebifyBuilderBase<T>> {
    var clientId: String
    var clientSecret: String

    fun clientId(clientId: String): T
    fun clientSecret(clientSecret: String): T
}