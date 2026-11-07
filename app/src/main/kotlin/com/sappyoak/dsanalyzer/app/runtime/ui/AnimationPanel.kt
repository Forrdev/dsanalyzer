package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import com.sappyoak.dsanalyzer.app.runtime.listed
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimChannelSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimRequestSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimSlotSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationSnapshot
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget

internal fun LazyListScope.animationItems(
    snapshot: RuntimeSnapshot,
    target: AnimationTarget,
    hideDead: Boolean,
    onAnimate: (AnimationTarget) -> Unit
) {
    item(key = "animation-target") {
        TargetPicker(target, snapshot.characters.listed(hideDead), onAnimate)
    }

    val animation = snapshot.animation
    if (animation == null) {
        item(key = "animation-none") {
            Field("", if (target == AnimationTarget.Off) "Not reading" else "Not readable right now", ROW_PADDING)
        }
        return
    }

    item(key = "animation-summary") { Summary(animation) }

    header("animation-slots", "Slots (${animation.occupied.size} of ${animation.slots.size} in use)")
    items(animation.occupied, key = { "slot-${it.index}" }) { slot ->
        val orphan = slot in animation.unreferenced
        Mono(slot.line(), if (orphan) AnalyzerTheme.colors.failed else AnalyzerTheme.colors.textHi)
    }

    header("animation-requests", "Requests (${animation.requests.count { it.live }} live)")
    items(animation.requests, key = { "request-${it.entry}" }) { request ->
        Mono(request.line(), if (request.live) AnalyzerTheme.colors.textHi else AnalyzerTheme.colors.textLo)
    }

    header("animation-channels", "Channels")
    if (animation.channels.isEmpty()) {
        item(key = "channels-none") { Field("", "Channel table not readable", ROW_PADDING) }
    }
    items(animation.channels, key = { "channel-${it.index}" }) { Mono(it.line(), AnalyzerTheme.colors.textHi) }
}

@Composable
private fun TargetPicker(
    target: AnimationTarget,
    characters: List<CharacterSnapshot>,
    onAnimate: (AnimationTarget) -> Unit
) {
    var open by remember { mutableStateOf(false) }

    Row(modifier = HEADER_PADDING, verticalAlignment = Alignment.CenterVertically) {
        Text("Watching ${target.describe(characters)}", style = MaterialTheme.typography.titleSmall)
        Box {
            TextButton(onClick = { open = true }) { Text("Change") }
            DropdownMenu(expanded = open, onDismissRequest = { open = false }) {
                val choices = listOf(AnimationTarget.Player, AnimationTarget.Off) +
                        characters.map { AnimationTarget.Character(it.handle) }
                choices.forEach { choice ->
                    DropdownMenuItem(
                        text = { Text(choice.describe(characters)) },
                        onClick = {
                            open = false
                            onAnimate(choice)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Summary(animation: AnimationSnapshot) {
    Field("Play speed", "%.2f".format(animation.playSpeed), ROW_PADDING)
    Field("Weight sum", "%.3f".format(animation.weightSum), ROW_PADDING)
    if (animation.stacks > 0) {
        Text(
            text = "Poopwalk: ${animation.stacks} stuck slot(s), walking at 1/${animation.stacks + 1} speed",
            style = MaterialTheme.typography.bodySmall,
            color = AnalyzerTheme.colors.failed,
            modifier = ROW_PADDING
        )
    }
    animation.unreferenced.takeIf { it.isNotEmpty() }?.let { slots ->
        Field("Held by nothing", slots.joinToString { "#${it.index}" }, ROW_PADDING)
    }
}

private fun LazyListScope.header(key: String, title: String) = item(key = key) {
    Text(title, style = MaterialTheme.typography.titleSmall, modifier = HEADER_PADDING)
}

@Composable
private fun Mono(line: String, color: Color) {
    Text(line, style = AnalyzerTheme.text.monoSmall, color = color, modifier = ROW_PADDING)
}

private fun AnimationTarget.describe(characters: List<CharacterSnapshot>): String = when (this) {
    AnimationTarget.Off -> "nobody"
    AnimationTarget.Player -> "the player"
    is AnimationTarget.Character -> characters.firstOrNull { it.handle == handle }
        ?.let { "${it.modelName} %08X".format(handle) }
        ?: "%08X (gone)".format(handle)
}

private fun AnimSlotSnapshot.line(): String {
    val anim = animationId?.toString() ?: "-"
    val played = control?.let {
        "t %6.3f  w %5.3f  x%4.2f  %s".format(
            it.localTime, it.masterWeight, it.playbackSpeed, if (it.looping) "loop" else "once"
        )
    } ?: "no control"
    val held = channel?.let { "ch $it" } ?: "ch -"
    val fade = if (fadeFrom.isNone) "" else "  from $fadeFrom %.2f/%.2f".format(fadeRemaining, fadeDuration)
    return "#%02d %-9s %-8s %s  %s  pri %d  %.2fs%s".format(
        index, state.name, anim, played, held, priority, elapsed, fade
    )
}

private fun AnimRequestSnapshot.line(): String =
    "entry %2d  %-7s  anim %-8d %-8s ch %s".format(
        entry, band?.let { "band $it" } ?: "unbanded", animationId, if (live) "live" else "disabled", channel
    )

private fun AnimChannelSnapshot.line(): String =
    "ch %d  %s  slot %s  fade %.2f".format(index, handle, slot, fadeWeight)
