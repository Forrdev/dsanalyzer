package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

public object WorldState {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldState",
        base = Signature.parse(
            name = "WorldState",
            pattern = "8B 54 24 10 8B C8 F7 D9 39 8A B8 0E 00 00 B3 01 0F 95 C2 8B 0D ?? ?? ?? ?? 80 B9 A5 0B 00 00 00",
            target = SignatureTarget.Embedded(0x15)
        ),
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xB90
    )

    public const val LastBonfire: Int = 0xB04

    /** X, Y and Z consecutively from here */
    public const val StablePosition: Int = 0xB70
    public const val StableAngle: Int = 0xB84
}

public object WorldArea {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldArea",
        base = Signature.parse(
            name = "WorldArea",
            pattern = "8B 48 04 8B 11 50 8B 42 34 FF D0 C7 46 2C 00 00 00 00 C3 8B 0D ?? ?? ?? ?? " +
                    "8B 89 50 0B 00 00 32 C0 83 79 28 00",
            target = SignatureTarget.Embedded(0x15)
        ),
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0xA20
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
        base = Signature.parse(
            name = "DeathCam",
            pattern = "88 5D 15 88 5D 16 88 5D 17 A1 ?? ?? ?? ?? 3B C3",
            target = SignatureTarget.Embedded(0xA)
        ),
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x50
    )

    public const val Active: Int = 0x40
}