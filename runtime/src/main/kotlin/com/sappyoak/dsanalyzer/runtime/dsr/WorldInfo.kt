package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** One `NS_FRPG::WorldAreaInfo` */
internal const val WORLD_AREA_INFO_SIZE: Int = 0x40

/** One `NS_FRPG::WorldBlockInfo` */
internal const val WORLD_BLOCK_INFO_SIZE: Int = 0x70

/** No map in this slot. All three of [WorldInfoOwner]'s read this when empty */
public const val NO_MAP: Int = -1

/** Through [WorldInfoOwner.WarpTarget] */
private const val WORLD_INFO_OWNER_SIZE = 0x0E90

public object WorldInfoOwner {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldInfoOwner",
        base = DSRSignatures.FieldArea,
        offsets = listOf(0L, FieldArea.WorldInfoOwnerPointer.toLong()),
        lifetime = Lifetime.World,
        size = WORLD_INFO_OWNER_SIZE
    )

    public const val AreaCount: Int = 0x08
    public const val AreasPointer: Int = 0x10
    public const val BlockCount: Int = 0x18
    public const val BlocksPointer: Int = 0x20

    public const val CurrentMap: Int = 0x0E78
    public const val PreviousMap: Int = 0x0E7C
    public const val WarpTarget: Int = 0x0E80

    public val Areas: GameTable = GameTable(
        name = "WorldAreaInfo",
        shape = TableShape.Counted(lengthAt = AreaCount, firstAt = AreasPointer, indirect = true),
        elements = TableElements.Inline(WORLD_BLOCK_INFO_SIZE),
        limit = 64
    )

    public fun mapOf(slot: Int): MapId? {
        if (slot == NO_MAP) return null
        return MapId.of(area = (slot ushr 24) and 0xFF, block = (slot ushr 16) and 0xFF)
    }
}

/** One 'NS_FRPG::WorldAreaInfo' */
public object WorldAreaInfo {
    public const val MapId: Int = 0x08
    /** The area number as a byte */
    public const val AreaCode: Int = 0x0B
}

/**
 * One 'NS_FRPG::WorldBlockInfo
 */
public object WorldBlockInfo {
    public const val MapId: Int = 0x08
    public const val OwnerPointer: Int = 0x10
    public const val AreaInfoPointer: Int = 0x18

    /** Which of the seventeen blocks this is */
    public const val BlockIndex: Int = 0x20

    public const val BlockInArea: Int = 0x24

    /** `NS_FRPG::MsbResCap*`. Null on every block that is not resident */
    public const val MapDataPointer: Int = 0x28

    public const val McgPointer: Int = 0x30
    public const val McpPointer: Int = 0x38
    public const val NvmbndPointer: Int = 0x40
    public const val BreakobjPointer: Int = 0x48

    public const val Loaded: Int = 0x60
}