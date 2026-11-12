package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class LivePositionLayout(
    public val chrCtrl: GamePointer,
    public val warp: Int,
    public val warpPosition: Int,
    public val warpAngle: Int,
    /** The ChrPosData pointer */
    public val posData: Int,
    public val position: Int,
    public val angle: Int,
    /** The 'hkpCharacterProxy' */
    public val proxy: Int,
    public val proxyVelocity: Int,
    public val velocity: Int,
    public val airTimer: Int
)
