package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.shared.math.Frustum
import com.sappyoak.dsanalyzer.shared.math.Mat4
import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * The follow camera as of one tick
 */
public data class CameraSnapshot(
    public val position: Vec3,
    /**
     * Pitch, yaw, and roll in radians.
     * This isn't sufficient on its own. Building a projection from euler angels needs the rotation order,
     * the handedness and the frustum guessed correctly
     */
    public val orientation: Vec3,
    /**
     * The pitch the camera is being pulled toward, which differs from [orientation] only while it is swinging.
     * There is no matching yaw kept here
     */
    public val targetPitch: Float,
    /** Camera-to-world, read whole */
    public val transform: Mat4,
    public val frustum: Frustum
) {
    public val worldToClip: Mat4 get() = transform.orthonormalInverse * frustum.projection
}
