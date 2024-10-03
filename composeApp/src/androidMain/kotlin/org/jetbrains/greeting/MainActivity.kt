package org.jetbrains.greeting

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.greeting.responses.Item
import org.jetbrains.greeting.responses.SpotifySearchResult
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = this.applicationContext as Application
        val webify = Webify
            .init(
                app,
                clientId = "",
                clientSecret = ""
            )

        lifecycleScope.launch {
            val result = webify.searchForTrack("Imagine Dragons")
            setContent {
                if (result.isSuccess()) {
                    val success = result as ApiResult.Success<SpotifySearchResult>
                    success.let {
                        App(it.data.tracks.items)
                    }
                } else {
                    Log.d("TAG", "no bueno")
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