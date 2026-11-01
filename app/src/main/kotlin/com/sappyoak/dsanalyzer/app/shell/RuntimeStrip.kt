package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.runtime.RuntimeState
import com.sappyoak.dsanalyzer.app.runtime.ui.label
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.app.ui.theme.Indicator
import com.sappyoak.dsanalyzer.app.ui.theme.of

private val STRIP_HEIGHT = 30.dp
private val DOT = 7.dp
private val RULE_HEIGHT = 14.dp
private const val LOW_HEALTH_DENOMINATOR = 5

@Composable
internal fun RuntimeStrip(state: RuntimeState) {
    val colors = AnalyzerTheme.colors
    val snapshot = state.snapshot

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(STRIP_HEIGHT)
            .background(colors.chrome)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val indicator = when {
            !state.live -> Indicator.Absent
            snapshot == null -> Indicator.Pending
            else -> Indicator.Resident
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            modifier = Modifier.padding(end = 14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(DOT)
                    .clip(CircleShape)
                    .background(colors.of(indicator))
            )
            state.attached?.let { game ->
                Text(game.edition.name, style = AnalyzerTheme.text.label, color = colors.textMid)
            }
            Text(
                text = state.connection(),
                style = AnalyzerTheme.text.monoSmall,
                color = colors.textLo
            )
        }

        if (snapshot != null) {
            Rule()
            Reading(snapshot.place.label, colors.textHi)

            Rule()
            Reading(formatInGameTime(snapshot.inGameTimeMillis), colors.textMid)

            snapshot.player?.let { player ->
                Rule()
                Labelled("HP") {
                    val max = player.attributes?.healthMax
                    Text(
                        text = player.health.toString(),
                        style = AnalyzerTheme.text.monoSmall,
                        color = if (max != null && player.health * LOW_HEALTH_DENOMINATOR <= max) {
                            colors.failed
                        } else {
                            colors.textHi
                        }
                    )
                    if (max != null) {
                        Text("/ $max", style = AnalyzerTheme.text.monoSmall, color = colors.textLo)
                    }
                }
            }

            snapshot.loadQueue?.let { queue ->
                Rule()
                Labelled("blocks") {
                    Text(
                        text = "${queue.resident.size} resident",
                        style = AnalyzerTheme.text.monoSmall,
                        color = colors.resident
                    )
                    if (queue.loading.isNotEmpty()) {
                        Text(
                            text = "${queue.loading.size} loading",
                            style = AnalyzerTheme.text.monoSmall,
                            color = colors.pending
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        
        state.flagLog.lastOrNull()?.let { entry ->
            Text(
                text = "flag ${entry.flagId} → ${if (entry.set) "on" else "off"}",
                style = AnalyzerTheme.text.monoSmall,
                color = colors.textLo
            )
        }
    }
    HorizontalDivider(color = colors.border)
}

@Composable
private fun Field(name: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = name,
            style = AnalyzerTheme.text.label,
            color = AnalyzerTheme.colors.textLo
        )
        Text(
            text = value,
            style = AnalyzerTheme.text.monoSmall,
            color = AnalyzerTheme.colors.textMid
        )
    }
}

@Composable
private fun Rule() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(RULE_HEIGHT)
            .background(AnalyzerTheme.colors.border)
    )
}

@Composable
private fun Reading(value: String, color: Color) {
    Text(
        text = value,
        style = AnalyzerTheme.text.monoSmall,
        color = color,
        modifier = Modifier.padding(horizontal = 14.dp)
    )
}

@Composable
private fun Labelled(name: String, value: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(horizontal = 14.dp)
    ) {
        Text(name, style = AnalyzerTheme.text.label, color = AnalyzerTheme.colors.textLo)
        value()
    }
}

private fun RuntimeState.connection(): String = when {
    !live -> "not attached"
    snapshot == null -> "reading"
    else -> "attached"
}

internal fun formatInGameTime(millis: Int): String {
    val seconds = millis.coerceAtLeast(0) / 1000
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val remainder = seconds % 60

    return if (hours > 0) {
        "$hours:${minutes.pad()}:${remainder.pad()}"
    } else "$minutes:${remainder.pad()}"
}

private fun Int.pad(): String = if (this < 10) "0$this" else "$this"
