package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.runtime.session.effects.SpecialEffectSnapshot
import com.sappyoak.dsanalyzer.shared.math.Vec3


public data class CharacterSnapshot(
    public val handle: Int,
    public val modelId: Int,
    public val npcParamId: Int,
    public val position: Vec3,
    public val health: Int,
    public val healthMax: Int,
    public val stamina: Int,
    public val characterType: Int,
    public val teamType: Int,
    /** The [handle] this character is targeting, or null */
    public val targetHandle: Int?,
    public val isPlayer: Boolean,
    public val effects: SpecialEffectSnapshot?
) {
    public val alive: Boolean get() = health > 0
    public val modelName: String get() = "c%04d".format(modelId)
}
