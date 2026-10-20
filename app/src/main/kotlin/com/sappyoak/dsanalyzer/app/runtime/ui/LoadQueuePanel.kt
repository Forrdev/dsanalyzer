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
import com.sappyoak.dsanalyzer.runtime.session.BlockState
import com.sappyoak.dsanalyzer.runtime.session.HeldBy
import com.sappyoak.dsanalyzer.runtime.session.LoadQueueSnapshot

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

        val releasing = queue.releasing
        if (releasing.isNotEmpty()) {
            Text(
                text = "Releasing: ${releasing.joinToString { it.label() }} - resident but no longer wanted",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        val active = queue.blocks.filter { it.active }
        if (active.isEmpty()) {
            Text("No block is loaded or wanted", style = MaterialTheme.typography.bodySmall)
            return@Column
        }

        Text(
            text = counts(queue),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        active.forEach { Field(it.label(), it.describe()) }
    }
}

private fun counts(queue: LoadQueueSnapshot): String = buildList {
    add("${queue.resident.size} resident")
    if (queue.loading.isNotEmpty()) add("${queue.loading.size} loading")
    if (queue.unloading.isNotEmpty()) add("${queue.unloading.size} unloading")
    if (queue.queued.isNotEmpty()) add("${queue.queued.size} queued")
}.joinToString() + " of ${queue.blocks.size} blocks"

private fun BlockLoad.label(): String =
    mapId?.let { "Block $index (${it.name})" } ?: "Block $index"

private fun BlockLoad.describe(): String = buildList {
    add(
        when (state) {
            BlockState.Absent -> "absent"
            BlockState.Loading -> "loading (stage $stage)"
            BlockState.Resident -> "resident"
            BlockState.Unloading -> "unloading (stage $stage)"
            BlockState.Unknown -> "unrecognised stage $stage"
        }
    )
    reason()?.let { add("held by $it") }
    if (queued) add("queued")
    if (releasing) add("releasing")
    if (suppressed) add("suppressed")
}.joinToString(" - ")

private fun BlockLoad.reason(): String? = when (heldBy) {
    HeldBy.CurrentMap -> "current map"
    HeldBy.PreviousMap -> "previous map"
    HeldBy.WarpTarget -> "warp target"
    HeldBy.Forced -> "a force-load"
    HeldBy.Pinned -> "a pin"
    HeldBy.Referenced -> "a reference"
    HeldBy.Nothing -> null
}