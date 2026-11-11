package com.sappyoak.dsanalyzer.runtime.dsr


import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** The MSB part size, the same as PTDE. DSR keeps the MSB file raw in memory */
internal const val MAP_PART_SIZE: Int = 0xC0

/** 'NS_FRPG::MsbResCap', found at `WorldBlockChr.MapDataPointer` */
public object MsbResCap {
    public const val PartsList: Int = 0x48
}

/** Each slot is an offset from the list start, not a pointer. The count includes a 0 terminator */
public object MapParts {
    public val All: GameTable = GameTable(
        name = "MapParts",
        shape = TableShape.ParamList(lengthAt = 0x08, firstAt = 0x0C),
        elements = TableElements.Offsets,
        limit = 4096
    )
}

public object MapPart {
    public const val Type: Int = 0x04
    public const val IndexInType: Int = 0x08
    public const val Position: Int = 0x14

    /** An offset from the part */
    public const val TypeParams: Int = 0x5C

    /** The name string, which the name offset at +0x00 points to */
    public const val Name: Int = 0x64
    public const val EntityId: Int = 0x78
    public const val NpcParamId: Int = 0x9C
    public const val ThinkParamId: Int = 0xA0
}

public object MapPartType {
    public const val Enemy: Int = 2
    public const val Collision: Int = 5
}
