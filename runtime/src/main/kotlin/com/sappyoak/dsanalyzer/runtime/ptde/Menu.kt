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
}
