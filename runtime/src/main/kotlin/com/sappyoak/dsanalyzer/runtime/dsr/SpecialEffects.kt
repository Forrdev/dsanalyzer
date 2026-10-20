package com.sappyoak.dsanalyzer.runtime.dsr

import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** through [SpecialEffects.StateInfoCounts] */
internal const val SPECIAL_EFFECT_SIZE: Int = 0x28

/** One active effect, and the distance to the next */
internal const val EFFECT_NODE_SIZE: Int = 0x50

public object SpecialEffects {
    public const val HeadPointer: Int = 0x08
    /** Back to the [ChrIns] these effects are on */
    public const val OwnerPointer: Int = 0x18

    public const val StateInfoCounts: Int = 0x20

    public val Active: GameTable = GameTable(
        name = "ActiveSpecialEffects",
        shape = TableShape.Linked(headAt = HeadPointer, nextAt = EffectNode.Next),
        elements = TableElements.Inline(EFFECT_NODE_SIZE),
        limit = 128
    )
}

public object EffectNode {
    public const val Remaining: Int = 0x00
    public const val Total: Int = 0x04
    public const val Magnitude: Int = 0x0C
    public const val Duration: Int = 0x10

    public const val Flags: Int = 0x14
    public const val EffectId: Int = 0x30
    public const val ParamRow: Int = 0x38
    public const val Next: Int = 0x40
    public const val Previous: Int = 0x48
}

public object EffectNodeFlags {
    public val Excluded: Int = 0x800C0003u.toInt()
    public const val Live: Int = 0x4
}