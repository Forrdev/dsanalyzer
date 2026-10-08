package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/**
 * Bytes to fetch for one part.
 *
 * Parts are **not** a uniform size, the type-specific tail differs by type, and is why the format
 * indexes them rather than striding them. This is a read width covering the common header
 * through [MapPart.ThinkParamId], not a stride, and nothing may iterate with it
 */
internal const val MAP_PART_SIZE = 0xC0

/**
 * 'NS_FRPG::MsbResCap' -- One map block's loaded MSB, reached from [WorldBlock.MapDataPointer]
 *
 * The runtime structure is the MSB file image with its offsets relocated to pointers, so the
 * layout the MSB reader in ':formats' already describes is the layout in memory.
 *
 * No [GamePointer] reaches this. A character's own 'MapDataPointer' is null on the player, so
 * the direct route is through a block: [WorldChrMan.Blocks], then [WorldBlock.MapDataPointer], then
 * here. This also gets the blocks the player is *not* int
 */
public object MsbResCap {
    /**
     * 'MODEL_PARAM_ST', the first list in the file.
     *
     * '+0x18' and '+0x1C' hold the same address, because the file begins with this list at offset
     *  zero, so the base of the loaded image and the models list are the same place
     */
    public const val ModelsList: Int = 0x18

    public const val EventsList: Int = 0x20
    public const val RegionsList: Int = 0x24
    public const val PartsList: Int = 0x28
}

public object MapParts {
    /**
     * Resolved against the address at [MsbResCap.PartsList], not against a pointer of its own
     */
    public val All: GameTable = GameTable(
        name = "MapParts",
        shape = TableShape.ParamList(lengthAt = LengthAt, firstAt = FirstSlot),
        elements = TableElements.Pointers,
        limit = 4096
    )

    public const val NamePointer: Int = 0x04
    public const val LengthAt: Int = 0x08
    public const val FirstSlot: Int = 0x0C
}

/**
 * One placed part
 *
 * [Type] says which kind, and the rest is read accordingly. An enemy has [NpcParamId] and
 * [ThinkParamId], a map piece has a [SourcePath] and those two read as nothing. Read [Type] first
 *
 * A part carries its strings inline and points at them. [NamePointer] holds 'part + 0x64' and
 * [SourcePathPointer] holds 'part + 0x6C'. That self-reference is the cheapest way to test whether
 * an address really is a part start.
 *
 * [Position] is three consecutive floats, unlike [ChrIns.PositionX]. These are the values the character
 * constructor copies *into* that transform, so this is where a spawn point is staed and that is where
 * it ends up
 */
public object MapPart {
    public const val NamePointer: Int = 0x00
    public const val Type: Int = 0x04

    public const val SourcePathPointer: Int = 0x10

    public const val Position: Int = 0x14
    public const val Rotation: Int = 0x20
    public const val Scale: Int = 0x2C

    /** The MSB part name as ASCII, `"c1000_0005"` for an enemy and `"m2000B1"` for a map piece */
    public const val Name: Int = 0x64

    /**
     * The original asset path, which names the block it came from —
     * `N:\FRPG\data\Model\map\m10_01_00_00\sib\...`.
     *
     * Present on map pieces and empty on the enemy parts read so far, so it is a bonus rather than
     * a way to identify a part's block. [EntityId] divided by 10000 gives the area and block for
     * any part that has one
     */
    public const val SourcePath: Int = 0x6C

    /** The id EMEVD scripts reference. `1010964` is area 10, block 1 — the id over 10000 */
    public const val EntityId: Int = 0x78

    /** Equal to [ChrIns.NpcParamId] on the character built from this part */
    public const val NpcParamId: Int = 0x9C

    public const val ThinkParamId: Int = 0xA0
}
public object MapPartType {
    public const val MapPiece: Int = 0
    public const val Enemy: Int = 2
    public const val Collision: Int = 5

    public const val ConnectCollision: Int = 11
}