package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.runtime.layout.ClockLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView

private const val MILLIS_PER_SECOND = 1000

internal class ClockSource(private val layout: ClockLayout, private val tickHz: Int) {
    private val gameData = StructView(layout.pointer)

    fun refresh(memory: GameMemory): Boolean = gameData.refresh(memory)
    fun timeMillis(): Int = if (gameData.isPresent) gameData.int(layout.inGameTimeMillis) else 0
    fun frame(): Long = timeMillis().toLong() * tickHz / MILLIS_PER_SECOND
}
