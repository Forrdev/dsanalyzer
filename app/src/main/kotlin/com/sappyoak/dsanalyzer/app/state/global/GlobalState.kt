package com.sappyoak.dsanalyzer.app.state.global

import com.sappyoak.dsanalyzer.app.config.HotKeyBinding
import com.sappyoak.dsanalyzer.app.config.HotKeySettings
import com.sappyoak.dsanalyzer.app.state.workspace.InstallationEntry
import kotlinx.serialization.Serializable

@Serializable
data class GlobalState(
    val hotkeys: HotKeySettings = HotKeySettings(),
    val capturingHotKey: HotKeyBinding? = null,
    val installations: List<InstallationEntry> = emptyList(),
    val firstRun: FirstRunSequence? = null
)