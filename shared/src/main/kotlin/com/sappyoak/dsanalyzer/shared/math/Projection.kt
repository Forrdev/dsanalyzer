package com.sappyoak.dsanalyzer.shared.math

public data class Frustum(
    /** Vertical field of view */
    public val fovRadians: Float,
    public val aspect: Float,
    public val near: Float,
    public val far: Float
) {
    public val projection: Mat4 get() = Mat4.perspective(fovRadians, aspect, near, far)
}

public data class ScreenPoint(
    public val x: Float,
    public val y: Float,
    /** Clip depth. 0 at the near plane, 1 at the far plane. For drawing order not occlusion */
    public val depth: Float
)

/**
 * A world point's place on the window, or null when it is not on the window
 *
 * The receiver is a **world-to-clip** transform: a camera's [Mat4.orthonormalInverse] compose with
 * its [Frustum.projection]
 *
 * Culling happens in clip space, before the divide because that is where it is exact and cheap
 */
public fun Mat4.project(point: Vec3, width: Int, height: Int): ScreenPoint? {
    val clip = transform(point.asPoint())
    if (clip.w <= 0f) return null
    if (clip.x < -clip.w || clip.x > clip.w) return null
    if (clip.y < -clip.w || clip.y > clip.w) return null

    val inverseW = 1f / clip.w
    return ScreenPoint(
        x = (clip.x * inverseW + 1f) * 0.5f * width,
        y = (1f - clip.y * inverseW) * 0.5f * height,
        depth = clip.z * inverseW
    )
}