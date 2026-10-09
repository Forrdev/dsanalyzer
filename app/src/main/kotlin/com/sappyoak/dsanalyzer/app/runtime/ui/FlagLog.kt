package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.FlagEntry
import com.sappyoak.dsanalyzer.game.world.WorldRef
import com.sappyoak.dsanalyzer.runtime.session.loadedMap

private val FRAME_WIDTH = 96.dp
private val STATE_WIDTH = 56.dp

@Composable
internal fun FlagLog(entries: List<FlagEntry>, onFollow: (WorldRef) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (entries.isEmpty()) "No flags have changed yet" else "Flag changes",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        entries.forEach { entry ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "f${entry.frame}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.width(FRAME_WIDTH)
                )
                Text(
                    text = if (entry.set) "on" else "off",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(STATE_WIDTH)
                )
                TextButton(onClick = { onFollow(WorldRef.EventFlag(entry.flagId, entry.place.loadedMap)) }) {
                    Text(entry.flagId.toString())
                }

                Text(
                    text = entry.place.label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}