package org.jetbrains.greeting


public actual class WebifyBuilder : WebifyBuilderBase {
    override var clientId: String = ""
    override var clientSecret: String = ""


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

    public actual fun build(): Webify {
        require(clientId.isNotEmpty())
        require(clientSecret.isNotEmpty())
        return Webify
            .createInstance()
            .setClientSecret(clientSecret)
            .setClientId(clientId)
            .init()
    }

}