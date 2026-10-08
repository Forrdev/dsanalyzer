package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/**
 * Every character the game currently has instantiated
 *
 * **Instantiated** is the limit of it. A map block can be loaded without its enemies
 * existing as characters yet, and those are not here, those are a part of the block's map data.
 * This answers who is in the world and not what could be
 */
public object WorldCharacters {
    public val Pointer: GamePointer = GamePointer(
        name = "Characters",
        base = ChrIns.Base,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = 0x20
    )

    /**
     * The characters themselves as 'ChrIns*'
     */
    public val All: GameTable = GameTable(
        name = "Characters",
        shape = TableShape.Range(beginAt = FirstPointer, endAt = EndPointer),
        elements = TableElements.Pointers
    )

    public const val FirstPointer: Int = 0x04
    public const val EndPointer: Int = 0x08

    /** One past the last slot the container could hold without growing */
    public const val CapacityPointer: Int = 0x0C
}