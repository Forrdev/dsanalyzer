package com.sappyoak.dsanalyzer.app.data.animations.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.data.animations.SetAnimations
import com.sappyoak.dsanalyzer.app.data.animations.animationLabel
import com.sappyoak.dsanalyzer.app.data.animations.linkOf
import com.sappyoak.dsanalyzer.app.data.animations.ticksAt
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.formats.tae.TaeEvent
import com.sappyoak.dsanalyzer.formats.tae.template.DecodedEvent
import com.sappyoak.dsanalyzer.formats.tae.template.DecodedParam

private val FRAMES_WIDTH = 96.dp
private val SECONDS_WIDTH = 110.dp
private val PARAM_INDENT = FRAMES_WIDTH + SECONDS_WIDTH

/**
 * An animation's events in timeline order, each with its decoded parameters. Windows are stored in seconds
 * (on a 30 fps grid in both editions) and shown in the game's logic ticks
 */
@Composable
internal fun AnimationEvents(
    animations: SetAnimations,
    animationId: Long,
    events: List<DecodedEvent>,
    onFollow: (View) -> Unit
) {
    val ordered = events.sortedWith(compareBy({ it.event.startTime }, { it.event.endTime }))

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "${animationLabel(animationId)}  ·  ${events.size} event(s)  ·  frames at ${animations.tickHz} Hz",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
        )
        HorizontalDivider(color = AnalyzerTheme.colors.border)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(ordered) { event -> EventRow(animations, event, onFollow) }
        }
    }
}

@Composable
private fun EventRow(animations: SetAnimations, decoded: DecodedEvent, onFollow: (View) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp)) {
        Row {
            Text(
                text = decoded.event.frames(animations.tickHz),
                style = AnalyzerTheme.text.mono,
                modifier = Modifier.width(FRAMES_WIDTH)
            )
            Text(
                text = decoded.event.seconds(),
                style = AnalyzerTheme.text.mono,
                color = AnalyzerTheme.colors.textLo,
                modifier = Modifier.width(SECONDS_WIDTH)
            )
            Text(decoded.label, style = MaterialTheme.typography.bodyMedium, color = AnalyzerTheme.colors.textHi)
            if (decoded.sizeMismatch) {
                Text(
                    text = "  parameters do not fit the template",
                    style = AnalyzerTheme.text.label,
                    color = AnalyzerTheme.colors.failed
                )
            }
        }
        decoded.params.forEach { param -> ParamLine(param, animations.linkOf(param), onFollow) }
    }
}

@Composable
private fun ParamLine(param: DecodedParam, link: View?, onFollow: (View) -> Unit) {
    val known = param.known?.let { " (${it.name})" }.orEmpty()
    val modifier = Modifier.padding(start = PARAM_INDENT)
        .let { if (link != null) it.clickable { onFollow(link) } else it }
    Text(
        text = "${param.definition.name ?: "?"} = ${param.value}$known",
        style = AnalyzerTheme.text.monoSmall,
        color = if (link != null) AnalyzerTheme.colors.link else AnalyzerTheme.colors.textMid,
        modifier = modifier
    )
}

/** The event's window in ticks, or the single tick of an instant event */
private fun TaeEvent.frames(tickHz: Int): String {
    val start = ticksAt(startTime, tickHz)
    val end = ticksAt(endTime, tickHz)
    return if (end == start) "f$start" else "f$start–$end"
}

private fun TaeEvent.seconds(): String =
    if (endTime == startTime) "%.3fs".format(startTime) else "%.3f–%.3fs".format(startTime, endTime)
