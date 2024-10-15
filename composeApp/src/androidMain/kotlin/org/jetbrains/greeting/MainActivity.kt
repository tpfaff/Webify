package org.jetbrains.greeting

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val app = this.applicationContext as Application
        val webify =
            Webify.Builder
            .clientId("d20648cd9e9c479a8d1e1c555e6cff76")
            .clientSecret("ee225f0a5a1044cdb1ef2a94c8e31e60")
            .applicationContext(app)
            .build()

        lifecycleScope.launch {
            val result = webify.searchForTrack("Imagine Dragons")
            setContent {
                if (result.isSuccess) {
                    result.map { data ->
                        App(data.tracks.items)
                    }
                } else {
                    Log.d("TAG", "${result.exceptionOrNull()}")
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(listOf())
}