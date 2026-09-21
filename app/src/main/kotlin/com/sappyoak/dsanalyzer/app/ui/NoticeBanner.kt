package com.sappyoak.dsanalyzer.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
public fun NoticeBanner(
    messages: List<String>,
    onDismiss: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            messages.forEach { Text(it) }
            TextButton(onClick = onDismiss) { Text("Dismiss") }
        }
    }
}