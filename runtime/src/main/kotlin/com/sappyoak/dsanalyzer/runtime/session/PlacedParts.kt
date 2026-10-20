package com.sappyoak.dsanalyzer.runtime.session

import com.sappyoak.dsanalyzer.shared.math.Vec3

/**
 * An enemy a loaded block places, whether or not the gme has built a character out of it yet
 */
public data class PlacedEnemy(
    public val index: Int,
    /** The MSB part name */
    public val name: String,
    /** The id EMEVD references. Divided by 10000 it gives the area and block that own it */
    public val entityId: Int,
    public val position: Vec3,
    public val npcParamId: Int,
    /** Which NpcThinkParam row drives this enemy's AI */
    public val thinkParamId: Int
)

/** Every enemy one loaded block places, with how many parts were walked to find them */
public data class BlockRoster(
    public val blockIndex: Int,
    public val partCount: Int,
    public val enemies: List<PlacedEnemy>
)
