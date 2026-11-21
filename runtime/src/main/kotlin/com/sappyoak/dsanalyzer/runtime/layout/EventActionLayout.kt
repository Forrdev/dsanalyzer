package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

/**
 * The player's event-action record, which its ChrIns points to at [record].
 */
public data class EventActionLayout(
    public val chr: GamePointer,
    public val record: Int,
    public val type: Int,
    public val first: Int,
    public val second: Int
)
