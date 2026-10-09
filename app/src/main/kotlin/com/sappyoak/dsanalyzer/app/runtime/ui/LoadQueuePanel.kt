package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.BlockLoad
import com.sappyoak.dsanalyzer.runtime.session.LoadQueueSnapshot
import com.sappyoak.dsanalyzer.runtime.ptde.LoadStage

@Composable
internal fun LoadQueuePanel(queue: LoadQueueSnapshot?) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text("Load queue", style = MaterialTheme.typography.titleSmall)

        if (queue == null) {
            Text("Not readable", style = MaterialTheme.typography.bodySmall)
            return@Column
        }

        Field("Current map", queue.current?.name ?: "none")
        Field("Previous map", queue.previous?.name ?: "none")
        Field("Warp target", queue.warpTarget?.name ?: "none")

        if (queue.handingOver) {
            Text(
                text = "Two maps are resident while the game hands over",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        val active = queue.blocks.filter { it.active }
        if (active.isEmpty()) {
            Text("No block is loaded or requested", style = MaterialTheme.typography.bodySmall)
            return@Column
        }

        Text(
            text = "${queue.loaded.size} loaded, ${queue.pending.size} in flight " +
            "of ${queue.blocks.size} blocks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        active.forEach { Field("Block ${it.index}", it.describe()) }
    }
}

private fun BlockLoad.describe(): String {
    val marks = buildList {
        if (requested) add("requested")
        if (wanted) add("wanted")
        if (pinned) add("pinned")
    }
    val suffix = if (marks.isEmpty()) "" else " - ${marks.joinToString()}"
    return stageName() + suffix
}

/** Only the three stages pinned from both ends are named */
private fun BlockLoad.stageName(): String = when (stage) {
    LoadStage.Idle -> "idle"
    LoadStage.Loading -> "loading"
    LoadStage.Loaded -> "loaded"
    else -> "stage $stage"
}