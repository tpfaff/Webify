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
        val webify = Webify.Builder()
            .clientId("")
            .clientSecret("")
            .applicationContext(app)
            .build()

        lifecycleScope.launch {
            val result = Webify.getInstance().searchForTrack("Imagine Dragons")
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