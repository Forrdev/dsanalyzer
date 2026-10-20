package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** The holder, which is small and holds no effects itself */
internal const val SPECIAL_EFFECT_SIZE: Int = 0x20

/** One active effect, and the distance to the next one */
internal const val EFFECT_NODE_SIZE: Int = 0x40


/**
 * 'NS_FRPG::SpecialEffect' the holder every character has for the effects currently on them.
 *
 * Read on a character than than on the play as this hangs off [ChrIns] so it is valid for enemies as well
 */
public object SpecialEffects {
    public val Pointer: GamePointer = GamePointer(
        name = "SpecialEffect",
        base = PTDESignatures.ChrIns,
        offsets = THROUGH_CHARACTER + ChrIns.SpecialEffectPointer.toLong(),
        lifetime = Lifetime.World,
        size = SPECIAL_EFFECT_SIZE
    )

    public val Active: GameTable = GameTable(
        name = "ActiveSpecialEffects",
        shape = TableShape.Linked(headAt = HeadPointer, nextAt = EffectNode.Next),
        elements = TableElements.Inline(EFFECT_NODE_SIZE),
        limit = 128
    )

    public const val HeadPointer: Int = 0x04

    /** Back to the [ChrIns] these effects are on */
    public const val OwnerPointer: Int = 0x10

    /**
     * Ten three-bit saturating counter of how many effects fall in each 'stateInfo' bucket
     *
     * Not a bit mask, which is what it looks like. Every 'stateInfo' lookup in the engine
     * fast-rejects on these without walking the list.
     */
    public const val StateInfoCounts: Int = 0x14
}

public object EffectNode {
    public const val Remaining: Int = 0x00
    public const val Total: Int = 0x04

    public const val Magnitude: Int = 0x0C
    public const val Duration: Int = 0x10
    public const val Flags: Int = 0x14
    public const val EffectId: Int = 0x28
    public const val ParamRow: Int = 0x2C
    public const val Next: Int = 0x30
    public const val Previous: Int = 0x34
}

public object EffectNodeFlags {
    public val Excluded: Int = 0x800C0003u.toInt()
    public const val Live: Int = 0x4
}
