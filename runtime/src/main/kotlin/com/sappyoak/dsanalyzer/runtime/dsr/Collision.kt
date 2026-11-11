package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** A map collision instance, 'NS_FRPG::HitIns' */
public object HitIns {
    /** The low word is the part's index among its MSB's collision parts */
    public const val PartIndex: Int = 0x08
    /** The 'MsbResCap' of the map the part came frame */
    public const val Msb: Int = 0x10

    /** Persistent display bits: lo 0x02, hi 0x04; bit 0x80 is the game's own */
    public const val Display: Int = 0xC0

    /** A byte the game never touches, which the tool tags with the part's hit filter */
    public const val Tag: Int = 0xC1
    public const val SIZE: Int = 0xC8
}

public object WorldHitMan {
    public val Pointer: GamePointer = GamePointer(
        name = "WorldHitMan",
        base = DSRSignatures.WorldHitMan,
        offsets = listOf(0L),
        lifetime = Lifetime.World,
        size = 0x28
    )

    public val Areas: GameTable = GameTable(
        name = "HitAreas",
        shape = TableShape.Counted(lengthAt = 0x1C, firstAt = 0x20, indirect = true),
        elements = TableElements.Inline(0x20),
        limit = 64
    )
    public val Groups: GameTable = GameTable(
        name = "HitGroups",
        shape = TableShape.Counted(lengthAt = 0x10, firstAt = 0x18, indirect = true),
        elements = TableElements.Inline(0x98),
        limit = 64
    )
    public val Instances: GameTable = GameTable(
        name = "HitInstances",
        shape = TableShape.Counted(lengthAt = 0x48, firstAt = 0x50, indirect = true),
        elements = TableElements.Inline(HitIns.SIZE),
        limit = 4096
    )
}

public object AttackMan {
    public val Pointer: GamePointer = GamePointer(
        name = "AttackMan",
        base = DSRSignatures.AttackMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Session,
        size = 0x38
    )

    /** Non-zero runs the game's own attack-hit debug draw every frame */
    public const val HitboxDraw: Int = 0x30
}

/** The renderer global's object and its EzDraw debug layers */
public object Graphics {
    public val Pointer: GamePointer = GamePointer(
        name = "Graphics",
        base = DSRSignatures.GraphicsMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Process,
        size = 0x60
    )

    public const val EzDrawZero: Int = 0x58

    /** In an EzDraw layer: the scene handle a draw plan targets to render it */
    public const val EzDrawScene: Int = 0x08
}

public object HitColors {
    public const val HiFill: Int = -0x20
    public const val LoFill: Int = -0x10
    public const val HiWire: Int = 0x10
    public const val LoWire: Int = 0x20

    /** The span from [HiFill] to the end of [LoWire] */
    public const val SPAN: Long = 0x50
}
