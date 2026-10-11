package com.sappyoak.dsanalyzer.overlay

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.runtime.session.CollisionMesh

private val WIREFRAME = Color(0x993F8EFC)
private val WIREFRAME_WIDTH = 1.dp

@Composable
internal fun CollisionLayer(scene: OverlayScene) {
    val camera = scene.camera ?: return
    val collision = scene.collision ?: return
    if (collision.bodies.isEmpty()) return

    val projector = remember(collision.triangleBudget) { TriangleProjector(collision.triangleBudget) }
    val path = remember { Path() }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width.toInt()
        val height = size.height.toInt()
        if (width <= 0 || height <= 0) return@Canvas

        projector.reset()
        for (body in collision.bodies.nearest(camera.position, collision.radius)) {
            val mesh = body.mesh ?: continue
            // False means the budget is spent. Stopping here rather than carrying on is what makes
            // the nearest-first ordering mean anything
            if (!projector.append(mesh, camera.worldToClip, width, height)) break
        }

        if (projector.count == 0) return@Canvas

        path.reset()
        for (triangle in 0 until projector.count) {
            val at = triangle * CollisionMesh.CORNERS
            path.moveTo(projector.xs[at], projector.ys[at])
            path.lineTo(projector.xs[at + 1], projector.ys[at + 1])
            path.lineTo(projector.xs[at + 2], projector.ys[at + 2])
            path.close()
        }

        drawPath(path, WIREFRAME, style = Stroke(width = WIREFRAME_WIDTH.toPx()))
    }
}