package com.sappyoak.dsanalyzer.overlay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.rememberTextMeasurer

@Composable
internal fun WorldLayer(scene: OverlayScene) {
    val frame = remember { CollisionFrame() }
    val path = remember { Path() }
    val measurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        val camera = scene.camera
        val collision = scene.collision
        val occlude = scene.markers.occludeByCollision

        // Null when there is nothing to project, which also means nothing for a marker to hide
        // behind. The frame is still told, so a buffer from an earlier camera is never reused
        val built = when {
            camera == null || collision == null || collision.bodies.isEmpty() -> {
                frame.occlusion.abandon()
                false
            }
            else -> frame.build(
                view = collision,
                camera = camera,
                width = size.width.toInt(),
                height = size.height.toInt(),
                occlude = occlude
            )
        }

        if (built && collision != null && collision.draw) drawCollision(frame, collision, path)

        drawMarkers(
            scene = scene,
            measurer = measurer,
            occlusion = if (occlude && frame.occlusion.isReady) frame.occlusion else null
        )
    }
}