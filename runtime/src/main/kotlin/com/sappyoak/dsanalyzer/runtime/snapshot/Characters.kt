package com.sappyoak.dsanalyzer.runtime.snapshot

import com.sappyoak.dsanalyzer.runtime.snapshot.action.ActionSnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.ai.AISnapshot
import com.sappyoak.dsanalyzer.runtime.snapshot.effects.SpecialEffectSnapshot
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
    public val effects: SpecialEffectSnapshot?,
    public val action: ActionSnapshot? = null,
    public val ai: AISnapshot? = null
) {
    public val alive: Boolean get() = health > 0
    public val modelName: String get() = "c%04d".format(modelId)
}
