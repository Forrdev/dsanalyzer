package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object WorldState {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldState",
        base = PTDESignatures.WorldState,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xB9C
    )

    public const val LastBonfire: Int = 0xB04

    /** X, Y and Z consecutively from here */
    public const val StablePosition: Int = 0xB70
    public const val StableAngle: Int = 0xB84
}

public object WorldArea {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldArea",
        base = PTDESignatures.WorldArea,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xA2C
    )

    public const val Area: Int = 0xA12
    public const val World: Int = 0xA13

    /**
     * The two bytes as the map id the rest of the tool addresses things by.
     *
     * [world] is taken as the leading number and [area] as the second, so 10 and 2 is m10_02_00_00
     */
    public fun mapId(world: Int, area: Int): MapId = MapId.of(area = world, block = area)
}

public object DeathCam {
    public val Pointer: GamePointer = GamePointer(
        name = "DeathCam",
        base = PTDESignatures.DeathCam,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x60
    )

    public const val Active: Int = 0x40
}