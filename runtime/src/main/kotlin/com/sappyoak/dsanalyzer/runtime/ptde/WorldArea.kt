package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

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
