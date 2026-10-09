package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.FollowCam
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
    public val targetPitch: Float
)

internal fun cameraOf(view: StructView): CameraSnapshot = CameraSnapshot(
    position = view.vec3(FollowCam.PosX),
    orientation = view.vec3(FollowCam.Orientation),
    targetPitch = view.float(FollowCam.TargetPitch)
)