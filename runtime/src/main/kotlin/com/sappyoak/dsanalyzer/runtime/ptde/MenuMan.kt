package com.sappyoak.dsanalyzer.runtime.ptde

import com.sappyoak.dsanalyzer.native.memory.Signature
import com.sappyoak.dsanalyzer.native.memory.SignatureTarget
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.Lifetime

/**
 * 'NS_FRPG::MenuMan'
 *
 * Volatile for now until more testing is done, but it will probably at least be Session
 */
public object MenuMan {
    public val Pointer: GamePointer = GamePointer(
        name = "MenuMan",
        base = Signature.parse(
            name = "MenuMan",
            pattern = "E8 ?? ?? ?? ?? 8B 35 ?? ?? ?? ?? A1 ?? ?? ?? ?? 3B C3 74 0D",
            target = SignatureTarget.Embedded(7)
        ),
        offsets = listOf(0L),
        lifetime = Lifetime.Volatile,
        size = 0x240
    )

    /**
     * The nine entries 'IsFullScreenMenu' ORs together, in the game's own order
     *
     * Taken from 'FUN_00C29C10, which the debug overlay print as
     * `フルスクリーンメニュー起動中|IsFullscreenMenu:%d` — "fullscreen menu active".
     *
     * They are entries in the array at ARRAY_BASE] rather than named fields
     */
    public val FullScreenMenu: IntArray = intArrayOf(0x24, 0x28, 0x68, 0x70, 0x80, 0x84, 0x108, 0x120, 0x124)

    /**
     * Base of the 500-entry array that menu scripts write by index
     *
     * Script command '0x3EC' is set(index, value) and writes 'base + index * 4' after a bounds
     * check of '0 <= index < 500', so the array runs to '0x7EC', which is exactly where the next
     * named field begins
     */
    public const val ARRAY_BASE: Int = 0x1C

    /** the pause menu family's screen */
    public const val Screen: Int = 0x28


    /**
     * The maximum selectable quantity for the current interaction. How many of a stackable item you current hold and can drop,
     * or how many of a stackable item you have the souls to purchase
     */
    public const val CurrentMaxQuantity: Int = 0x204
    public const val DefaultQuantity: Int = 0x20C
    /**
     * Dwords in [Pointer]'s region
     */
    public const val Words: Int = 0x200 / Int.SIZE_BYTES
}