package org.jetbrains.greeting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import greetingkmp.composeapp.generated.resources.Res
import greetingkmp.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.greeting.responses.Item
import org.jetbrains.greeting.responses.Track
import org.jetbrains.greeting.responses.Tracks

@Composable
@Preview
fun App(searchResults: List<Item>) {
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
                    }
                }
            }
        }
    }
}