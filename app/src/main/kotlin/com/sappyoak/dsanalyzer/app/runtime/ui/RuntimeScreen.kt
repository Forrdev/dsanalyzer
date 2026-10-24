package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.RuntimeState
import com.sappyoak.dsanalyzer.game.world.WorldRef

private val LIST_END = 7.dp

@Composable
public fun RuntimeScreen(
    state: RuntimeState,
    onFollow: (WorldRef) -> Unit,
    onReadPlacedEnemies: () -> Unit,
    onReadCollision: () -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        panel("attachment") { AttachmentPanel(state, onFollow) }

        val snapshot = state.snapshot
        if (snapshot == null) {
            item(key = "waiting") {
                Text(
                    text = state.problem ?: "Connect to a running game",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            return@LazyColumn
        }

        panel("player") { PlayerPanel(snapshot) }
        panel("camera") { CameraPanel(snapshot.camera) }
        panel("load-queue") { LoadQueuePanel(snapshot.loadQueue) }
        characterItems(snapshot.characters, state.placedEnemies, onReadPlacedEnemies)
        listEnd("characters-end")
        panel("collision") { CollisionPanel(state.collision, onReadCollision) }
        flagItems(state.flagLog, onFollow)
    }
}

private fun LazyListScope.panel(key: String, content: @Composable () -> Unit) = item(key = key) {
    Column {
        content()
        HorizontalDivider()
    }
}

private fun LazyListScope.listEnd(key: String) = item(key = key) {
    Column {
        Spacer(modifier = Modifier.height(LIST_END))
        HorizontalDivider()
    }
}

