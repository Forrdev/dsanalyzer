package com.sappyoak.dsanalyzer.app.connection.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.connection.ConnectionStatus

@Composable
public fun ConnectionStatusBar(status: ConnectionStatus) {
    Surface(modifier = Modifier.fillMaxWidth(), tonalElevation = 2.dp) {
        Text(
            text = status.describe(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

private fun ConnectionStatus.describe(): String = when (this) {
    ConnectionStatus.Off -> "Not connected"
    ConnectionStatus.Searching -> "Waiting for the game to start"
    is ConnectionStatus.Connected -> "Connected to ${edition.name} (pid $pid)"
    is ConnectionStatus.Refused -> "Found $executableName but could not connect: $reason"
    is ConnectionStatus.Unavailable -> "Connecting is unavailable: $reason"
}