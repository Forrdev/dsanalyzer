package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class CameraLayout(
    public val pointer: GamePointer,
    public val transform: Int,
    public val fieldOfView: Int,
    public val aspect: Int,
    public val near: Int,
    public val far: Int,
    public val smoothedPosition: Int?,
    public val orientation: Int?,
    public val targetPitch: Int?
)