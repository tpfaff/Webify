package org.jetbrains.greeting

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.jetbrains.greeting.responses.AccessToken

class TokenProvider {
    private var accessToken: AccessToken? = null
    private val mutex = Mutex()

    companion object{
        val instance: TokenProvider by lazy {
            TokenProvider()
        }
    }

    suspend fun saveToken(accessToken: AccessToken){
        mutex.withLock {
            this.accessToken = accessToken
        }
    }

    suspend fun getToken(): AccessToken? {
        return mutex.withLock {
            accessToken
        }
    }
}