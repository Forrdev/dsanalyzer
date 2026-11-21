package com.sappyoak.dsanalyzer.runtime.layout

import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer

public data class ClockLayout(
    public val pointer: GamePointer,
    public val inGameTimeMillis: Int
)
