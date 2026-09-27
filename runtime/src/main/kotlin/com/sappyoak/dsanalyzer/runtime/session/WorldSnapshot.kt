package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.shared.math.Vec3

public data class WorldSnapshot(
    public val stablePosition: Vec3,
    public val stableAngle: Float,
    public val lastBonfire: Int,
    public val deathCam: Boolean
)