package com.sappyoak.dsanalyzer.runtime.ptde.read

import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.FollowCam
import com.sappyoak.dsanalyzer.runtime.ptde.FrpgCam
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.shared.math.Frustum

internal fun cameraOf(view: StructView): CameraSnapshot = CameraSnapshot(
    position = view.vec3(FollowCam.PosX),
    orientation = view.vec3(FollowCam.Orientation),
    targetPitch = view.float(FollowCam.TargetPitch),
    transform = view.mat4(FrpgCam.Transform),
    frustum = Frustum(
        fovRadians = view.float(FrpgCam.FieldOfView),
        aspect = view.float(FrpgCam.Aspect),
        near = view.float(FrpgCam.Near),
        far = view.float(FrpgCam.Far)
    )
)