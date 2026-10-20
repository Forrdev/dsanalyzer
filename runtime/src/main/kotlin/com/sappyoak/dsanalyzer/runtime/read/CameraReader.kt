package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.layout.CameraLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.CameraSnapshot
import com.sappyoak.dsanalyzer.shared.math.Frustum

internal class CameraReader(private val layout: CameraLayout) {
    private val view = StructView(layout.pointer)

    fun refresh(memory: GameMemory): Boolean = view.refresh(memory)

    fun camera(): CameraSnapshot {
        val transform = view.mat4(layout.transform)

        return CameraSnapshot(
            position = transform.position,
            orientation = layout.orientation?.let { view.vec3(it) },
            targetPitch = layout.targetPitch?.let { view.float(it) },
            transform = transform,
            frustum = Frustum(
                fovRadians = view.float(layout.fieldOfView),
                aspect = view.float(layout.aspect),
                near = view.float(layout.near),
                far = view.float(layout.far)
            )
        )
    }
}