package com.sappyoak.dsanalyzer.runtime.ptde

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
        base = PTDESignatures.MenuMan,
        offsets = listOf(0L),
        lifetime = Lifetime.Volatile,
        size = 0x2E8
    )

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
     * Which kind of modal confirmation box is up.
     *
     * Known kinds:
     *  8 - an item-use yes/no box
     *  2 - the quitout confirmation
     *  9 - a non-input blocking box
     */
    public const val BoxKind: Int = 0x60

}