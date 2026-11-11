package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.EngineDrawStatus

/** The state of the game's own debug draw. What it draws is set in the settings */
@Composable
internal fun CollisionPanel(status: EngineDrawStatus?, onInstall: (Boolean) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("In-game draw", style = MaterialTheme.typography.titleSmall)
            when (status) {
                is EngineDrawStatus.Installed -> TextButton(onClick = { onInstall(false) }) { Text("Remove") }
                EngineDrawStatus.Removed, is EngineDrawStatus.Failed -> TextButton(onClick = { onInstall(true) }) { Text("Install") }
                EngineDrawStatus.Unsupported, null -> Unit
            }
        }

        when (status) {
            null -> Field("Code caves", "waiting for the session")
            EngineDrawStatus.Unsupported -> Field("Code caves", "not built for this edition")
            EngineDrawStatus.Removed -> Field("Code caves", "not installed")
            is EngineDrawStatus.Failed -> Field("Code caves", "failed: ${status.reason}")
            is EngineDrawStatus.Installed -> {
                Field("Code caves", "installed")
                Field("Collision parts tagged", status.tagged.toString())
            }
        }

        Text(
            text = "Collision, filters, colors and hitboxes are set in Settings, under In-game draw",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
