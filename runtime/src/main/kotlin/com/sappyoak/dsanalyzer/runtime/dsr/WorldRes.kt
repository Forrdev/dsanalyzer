package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** One `NS_FRPG::WorldBlockRes`.  */
internal const val WORLD_BLOCK_RES_SIZE: Int = 0x270

/** One `NS_FRPG::WorldAreaRes` */
internal const val WORLD_AREA_RES_SIZE: Int = 0xE8

public object WorldRes {
    public const val AreaCount: Int = 0x0E50

    public const val AreasPointer: Int = 0x0E58

    public const val BlockCount: Int = 0x0E68
    public const val BlocksPointer: Int = 0x0E70

    public val Blocks: GameTable = GameTable(
        name = "WorldBlockRes",
        shape = TableShape.Counted(lengthAt = BlockCount, firstAt = BlocksPointer, indirect = true),
        elements = TableElements.Inline(WORLD_BLOCK_RES_SIZE),
        limit = 64
    )
}

public object WorldBlockRes {
    public const val BlockInfoPointer: Int = 0x08

    public const val OwnerPointer: Int = 0x10
    public const val AreaResPointer: Int = 0x18
    public const val Suppress: Int = 0x70
    public const val ForceLoad: Int = 0x71

    /** Set while this block is named in [WorldInfoOwner.CurrentMap] or [WorldInfoOwner.PreviousMap] */
    public const val Requested: Int = 0x72

    /** Already set while [Stage] is still [LoadStage.Idle], so before staging begins */
    public const val Latched: Int = 0x73

    public const val WantLoaded: Int = 0x75
    public const val Stage: Int = 0x78
    public const val Teardown: Int = 0x7C
    public const val JobSlots: Int = 0x238
    public const val JobSlotCount: Int = 4
}

/**
 * Values of [WorldBlockRes.Stage]
 *
 * Loading runs '0 -> 1 -> 2 -> 3 -> 4 -> 6 -> 7' with [Loaded] being terminal and
 * [WorldBlockInfo.Loaded] flipping in the same frame.
 *
 * Unloading runs '7 -> 8 -> 5 -> 0' and '7 -> 9 -> 5 -> 0' so both 8 and 9 are unload
 * entry points and 5 seems to be the common late step
 *
 * An ordering caveat, on every unload the latch bytes cleared one sample *before*
 * [WorldBlockRes.Stage] left [Loaded], so the two momentarily disagree
 */
public object LoadStage {
    public const val Idle: Int = 0
    public const val Loaded: Int = 7

    private val LOADING = setOf(1, 2, 3, 4, 6)
    private val UNLOADING = setOf(5, 8, 9)

    public fun isLoading(stage: Int): Boolean = stage in LOADING
    public fun isUnloading(stage: Int): Boolean = stage in UNLOADING
    public fun isResident(stage: Int): Boolean = stage == Loaded
}