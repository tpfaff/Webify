package org.jetbrains.greeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val posts = ApiClient().getPosts()
            val track = ApiClient().searchForTrack("Imagine Dragons")
            setContent {
                App(posts)
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(listOf())
}