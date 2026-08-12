package com.sappyoak.dsanalyzer.app

import androidx.compose.ui.window.*

fun main() {
    application {
        Window(
            onCloseRequest = {
                exitApplication()
            },
            title = "DSAnalyzer"
        ) {}
    }
}