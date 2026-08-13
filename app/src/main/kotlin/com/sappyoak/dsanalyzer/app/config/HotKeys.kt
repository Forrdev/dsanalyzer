package com.sappyoak.dsanalyzer.app.config

import androidx.compose.ui.input.key.Key
import kotlinx.serialization.Serializable

@Serializable
data class HotKeySettings(
    val stopCapture: Long = Key.F3.keyCode,
    val toggleOverlay: Long = Key.F4.keyCode
) {
    fun matches(binding: HotKeyBinding, keyCode: Long): Boolean = when (binding) {
        HotKeyBinding.StopCapture -> keyCode == stopCapture
        HotKeyBinding.ToggleOverlay -> keyCode == toggleOverlay
    }

    fun codeFor(binding: HotKeyBinding): Long = when (binding) {
        HotKeyBinding.StopCapture -> stopCapture
        HotKeyBinding.ToggleOverlay -> toggleOverlay
    }

    fun with(binding: HotKeyBinding, keyCode: Long): HotKeySettings = when (binding) {
        HotKeyBinding.StopCapture -> copy(stopCapture = keyCode)
        HotKeyBinding.ToggleOverlay -> copy(toggleOverlay = keyCode)
    }
}

enum class HotKeyBinding(
    val label: String,
    val description: String
) {
    StopCapture(
        "Stop capture",
        "Ends the current capture"
    ),
    ToggleOverlay(
        "Toggle overlay",
        "Shows or hides the in-game overlay"
    )
}