package com.sappyoak.dsanalyzer.app.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
public fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(content = content)
    }
}