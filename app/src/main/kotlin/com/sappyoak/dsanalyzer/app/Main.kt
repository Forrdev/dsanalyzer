package com.sappyoak.dsanalyzer.app

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "dsanalyzer",
            state = rememberWindowState(size = DpSize(1280.dp, 800.dp))
        ) {
            AppRoot()
        }
    }
}

@Composable
private fun AppRoot() {
    MaterialTheme {
        Surface {
            Text("dsanalyzer")
        }
    }
}