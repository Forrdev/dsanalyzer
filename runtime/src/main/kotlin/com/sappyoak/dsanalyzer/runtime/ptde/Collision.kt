package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** A map collision instance, 'NS_FRPG::HitIns' */
public object HitIns {
    public const val PartIndex: Int = 0x04
    public const val Msb: Int = 0x08
    public const val EntryIndex: Int = 0x0C
    public const val BlockIndex: Int = 0x0F

    /** Persistent display bits: lo 0x02, hi 0x04, with this frame's 0x10/0x20 */
    public const val Display: Int = 0x80
    public const val Tag: Int = 0x81
    public const val SIZE: Int = 0x84
}

public object WorldHitMan {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldHitMan",
        base = PTDESignatures.WorldHitMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = 0x20
    )

    public val Areas: GameTable = GameTable(
        name = "HitAreas",
        shape = TableShape.Counted(lengthAt = 0x18, firstAt = 0x1C, indirect = true),
        elements = TableElements.Inline(0x10),
        limit = 64
    )
    public val Groups: GameTable = GameTable(
        name = "HitGroups",
        shape = TableShape.Counted(lengthAt = 0x08, firstAt = 0x0C, indirect = true),
        elements = TableElements.Inline(0x68),
        limit = 64
    )
    public val Instances: GameTable = GameTable(
        name = "HitInstances",
        shape = TableShape.Counted(lengthAt = 0x3C, firstAt = 0x40, indirect = true),
        elements = TableElements.Inline(HitIns.SIZE),
        limit = 4096
    )
}
