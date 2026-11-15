package com.sappyoak.dsanalyzer.overlay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
internal fun WorldLayer(scene: OverlayScene) {
    val measurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawTargetPanel(scene, measurer)
    }
}
