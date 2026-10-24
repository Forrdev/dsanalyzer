package com.sappyoak.dsanalyzer.app.runtime.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import com.sappyoak.dsanalyzer.app.runtime.OverlaySettings
import com.sappyoak.dsanalyzer.app.runtime.RuntimeStore
import com.sappyoak.dsanalyzer.app.runtime.overlayScene
import com.sappyoak.dsanalyzer.native.windows.ViewportSource
import com.sappyoak.dsanalyzer.overlay.GameOverlay

@Composable
public fun RuntimeOverlay(
    source: ViewportSource,
    runtime: RuntimeStore,
    settings: OverlaySettings,
    connected: Boolean
) {
    val state by runtime.state.collectAsState()
    val scene = remember(state, settings) {
        state.overlayScene(settings)
    }

    GameOverlay(source = source, scene = scene, enabled = connected && settings.enabled)
}
