package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/**
 * GameMan's request to spawn the player at a point. With [flag] set the next
 * load goes to the map at [map] and spawns at [position] facing [angle], and the
 * game clears [flag].
 */
public data class PointWarpLayout(
    public val map: Int,
    public val position: Int,
    public val angle: Int,
    public val flag: Int
)

/** The game's warp call and what it reads */
public data class WarpLayout(
    public val lastBonfire: FieldLayout,
    public val point: PointWarpLayout,
    public val function: GamePointer,
    public val owner: GamePointer,
    public val ownerRegister: CallRegister,
    public val kindRegister: CallRegister?
)
