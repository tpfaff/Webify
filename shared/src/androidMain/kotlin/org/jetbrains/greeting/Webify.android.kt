package org.jetbrains.greeting

import android.app.Application


public actual class WebifyBuilder : WebifyBuilderBase<WebifyBuilder> {
    override var clientId: String = ""
    override var clientSecret: String = ""
    private lateinit var context: Application


    override fun clientId(clientId: String): WebifyBuilder {
        return this.apply {
            this.clientId = clientId
        }
    }

    override fun clientSecret(clientSecret: String): WebifyBuilder {
        return this.apply {
            this.clientSecret = clientSecret
        }
    }

    public fun applicationContext(applicationContext: Application): WebifyBuilder {
        return this.apply {
            this.context = applicationContext
        }
    }

    public actual fun build(): Webify {
        require(clientId.isNotEmpty())
        require(clientSecret.isNotEmpty())
        require(::context.isInitialized)
        return Webify
                .createInstance()
                .setClientSecret(clientSecret)
                .setClientId(clientId)
                .init(context)
    }

}