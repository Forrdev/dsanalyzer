package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

private const val ENTRY_STRIDE: Int = Int.SIZE_BYTES

public data class MenuLayout(
    public val pointer: GamePointer,
    public val arrayBase: Int
) {
    public fun offsetOf(index: Int): Int = arrayBase + index * ENTRY_STRIDE
}
