package com.sappyoak.dsanalyzer.overlay

import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.ScreenPoint
import com.sappyoak.dsanalyzer.shared.math.Vec3
import com.sappyoak.dsanalyzer.shared.math.project

private const val STEM_HEIGHT = 1.7f

/** Within this, full strength */
private const val NEAR_DISTANCE = 15f

/** The least a distant marker fades to — still visible, no longer competing */
private const val MIN_STRENGTH = 0.3f

internal data class ProjectedMarker(
    val marker: WorldMarker,
    val head: ScreenPoint,
    val feet: ScreenPoint?,
    val distance: Float,
    /** One near the camera, falling to [MIN_STRENGTH] far away */
    val strength: Float
)

internal fun MarkerView.projectedOnto(
    worldToClip: Mat4,
    eye: Vec3,
    width: Int,
    height: Int
): List<ProjectedMarker> = markers.mapNotNull { marker ->
    val distance = eye.distanceTo(marker.position)
    if (distance > drawDistance) return@mapNotNull  null

    val head = worldToClip.project(marker.position.raisedBy(STEM_HEIGHT), width, height)
        ?: return@mapNotNull null

    ProjectedMarker(
        marker = marker,
        head = head,
        feet = worldToClip.project(marker.position, width, height),
        distance = distance,
        strength = strengthAt(distance, drawDistance)
    )
}.sortedByDescending { it.distance }

private fun Vec3.raisedBy(height: Float): Vec3 = Vec3(x, y + height, z)

private fun strengthAt(distance: Float, drawDistance: Float): Float {
    val span = drawDistance - NEAR_DISTANCE
    val nearness = ((drawDistance - distance) / span).coerceIn(0f, 1f)
    return MIN_STRENGTH + (1f - MIN_STRENGTH) * nearness
}