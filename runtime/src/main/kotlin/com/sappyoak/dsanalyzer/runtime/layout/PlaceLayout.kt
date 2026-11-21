package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer


public data class PlaceLayout(
    public val pointer: GamePointer,
    public val area: Int,
    public val block: Int,
    public val noMap: NoMapLayout? = null
)

public data class NoMapLayout(public val offset: Int, public val value: Int)
