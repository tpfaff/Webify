package org.jetbrains.greeting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import greetingkmp.composeapp.generated.resources.Res
import greetingkmp.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.greeting.responses.Item
import org.jetbrains.greeting.responses.Track
import org.jetbrains.greeting.responses.Tracks

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
@Preview
fun App(searchResults: List<Item>) {

    val coroutineScope = rememberCoroutineScope()
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
              //  val greeting = remember { Greeting().greet() }
                LazyColumn{
                    items(searchResults){
                        Text(it.name)
                        GlideImage(it.album.images.first().url, "",
                            Modifier.clickable {
                            coroutineScope.launch {
                                val result = ApiClient.getInstance().getTrackAnalysis(it.id)
                            }
                        })
                    }
                }
            }
        }
    }
}