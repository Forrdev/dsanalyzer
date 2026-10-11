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

import com.sappyoak.dsanalyzer.runtime.session.CollisionSnapshot

@Composable
internal fun CollisionPanel(collision: CollisionSnapshot?, onRead: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Collision", style = MaterialTheme.typography.titleSmall)
            TextButton(onClick = onRead) { Text(if (collision == null) "Read" else "Re-read") }
        }

        if (collision == null) {
            Text(
                text = "Not read yet - about two megabytes for one map block, so it is a deliberate read",
                style = MaterialTheme.typography.bodySmall
            )
            return@Column
        }

        Field("Bodies", collision.bodies.size.toString())
        Field("Triangles", collision.triangleCount.toString())

        if (collision.skipped > 0) {
            Field("Without geometry", "${collision.skipped} (shapes with no reader yet)")
        }
    }
}