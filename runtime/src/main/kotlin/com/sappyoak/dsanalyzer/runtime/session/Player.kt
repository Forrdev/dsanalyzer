package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.layout.Stat
import com.sappyoak.dsanalyzer.shared.math.Vec3

public data class PlayerSnapshot(
    public val character: CharacterSnapshot,
    public val angle: Float,
    public val playRegion: Int,
    public val animationSpeed: Float?,
    public val cheats: Set<CheatFlag>,
    public val attributes: AttributeSnapshot?
) {
    public val position: Vec3 get() = character.position
    public val health: Int get() = character.health
    public val stamina: Int get() = character.stamina
    public val characterType: Int get() = character.characterType
    public val teamType: Int get() = character.teamType
}

public data class AttributeSnapshot(
    public val healthMax: Int,
    public val healthBaseMax: Int,
    public val mp: Int,
    public val mpMax: Int,
    public val staminaMax: Int,
    public val staminaBaseMax: Int,
    public val stats: Map<Stat, Int>,
    public val soulLevel: Int,
    public val souls: Int,
    public val soulMemory: Int,
    public val humanity: Int,
    public val durability: Int,
    public val covenant: Int?,
    public val stance: Int?,
    public val gender: Int,
    public val startingClass: Int,
    public val physique: Int,
    public val gift: Int,
    public val resists: ResistSnapshot
)

public data class ResistSnapshot(
    public val poison: Int,
    public val bleed: Int,
    public val toxic: Int,
    public val curse: Int
)

public data class WorldSnapshot(
    public val stablePosition: Vec3,
    public val stableAngle: Float,
    public val lastBonfire: Int,
    public val deathCam: Boolean
)

