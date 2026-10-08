package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.GameTable
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime
import com.sappyoak.dsanalyzer.runtime.pointers.TableElements
import com.sappyoak.dsanalyzer.runtime.pointers.TableShape

/** One active effect, and the distance to the next one */
private const val NODE_SIZE = 0x40

/**
 * 'NS_FRPG::SpecialEffect' the holder every character has for the effects currently on them.
 *
 * Read on a character than than on the play as this hangs off [ChrIns] so it is valid for enemies as well
 */
public object SpecialEffects {
    public val Pointer: GamePointer = GamePointer(
        name = "SpecialEffectg",
        base = ChrIns.Base,
        offsets = THROUGH_CHARACTER + ChrIns.SpecialEffectPointer.toLong(),
        lifetime = Lifetime.World,
        size = 0x20
    )

    public val Active: GameTable = GameTable(
        name = "ActiveSpecialEffects",
        shape = TableShape.Linked(headAt = HeadPointer, nextAt = ActiveEffect.Next),
        elements = TableElements.Inline(NODE_SIZE),
        limit = 128
    )

    public const val HeadPointer: Int = 0x04

    /** Back to the [ChrIns] these effects are on */
    public const val OwnerPointer: Int = 0x10
}

/**
 * One applied effect. [Duration] and [DurationTotal] read '-1.0' for an effect with no timer
 */
public object ActiveEffect {
    public const val Duration: Int = 0x00
    public const val DurationTotal: Int = 0x04
    public const val Flags: Int = 0x14
    public const val EffectId: Int = 0x28
    public const val ParamRow: Int = 0x2C
    public const val Next: Int = 0x30

    public const val Previous: Int = 0x34
}


public object ActiveEffectFlags {
    /**
     * The bits that make the engine skip an effect when it accumulates them.
     *
     * Taken from 'EnemyIns' constructor which walks the chain and ignores any node where
     * 'flags and this' is non-zero
     *
     * Not a const val as the mask's high bit is set, so '0x800C0003' does not fit a signed Int and the
     * literal would have to be written as -0x7FF3FFFD` to compile. Going through UInt keeps the bit
     * pattern on the page, which is what we need to see when checking against the disassembly
     */
    public val Excluded: Int = 0x800C0003u.toInt()
}