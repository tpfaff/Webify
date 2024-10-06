package org.jetbrains.greeting

import android.app.Application
import android.content.Context

internal class ContextProvider {


    private lateinit var context: Application

    fun setContext(context: Application) {
        this.context = context
    }

    fun getContext(): Context {
        return context
    }

    companion object {
        private val instance = ContextProvider()
        fun getInstance(): ContextProvider {
            return instance
        }
    }
}
