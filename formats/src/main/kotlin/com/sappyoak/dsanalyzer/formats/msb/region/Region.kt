package com.sappyoak.dsanalyzer.formats.msb.region

import com.sappyoak.dsanalyzer.shared.math.Vec3

public data class Region(
    public val name: String,
    public val shape: Shape,
    public val position: Vec3,
    public val rotation: Vec3,
    public val entityId: Int?
)