package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.shared.math.Vec3

public data class PlayerSnapshot(
    public val position: Vec3,
    public val angle: Float,
    public val health: Int,
    public val stamina: Int,
    public val characterType: Int,
    public val teamType: Int,
    public val playRegion: Int,
    public val animationSpeed: Float?,
    public val cheats: Set<CheatFlag>,
    public val attributes: AttributeSnapshot?
)

public data class AttributeSnapshot(
    public val healthMax: Int,
    public val staminaMax: Int,
    public val soulLevel: Int,
    public val souls: Int,
    public val humanity: Int,
    public val covenant: Int,
    public val stance: Int
)
