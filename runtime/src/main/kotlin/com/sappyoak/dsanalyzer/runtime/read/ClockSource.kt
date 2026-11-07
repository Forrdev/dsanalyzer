package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.dsr.GameDataMan as DSRGameDataMan
import com.sappyoak.dsanalyzer.runtime.ptde.GameDataMan as PTDEGameDataMan

private const val MILLIS_PER_SECOND = 1000

internal class ClockSource private constructor(
    pointer: GamePointer,
    private val timeOffset: Int,
    private val tickHz: Int
) {
    private val gameData = StructView(pointer)

    fun refresh(memory: GameMemory): Boolean = gameData.refresh(memory)

    fun timeMillis(): Int = if (gameData.isPresent) gameData.int(timeOffset) else 0

    /**
     * The logic tick in-game time has reached. Counted in integers on purpose: dividing by a
     * fractional tick length loses a frame on the boundaries (66000 ms at 30 Hz comes out as
     * 1979.9999999999998, which truncates to 1979 for a moment that is frame 1980)
     */
    fun frame(): Long = timeMillis().toLong() * tickHz / MILLIS_PER_SECOND

    companion object {
        public fun of(edition: GameEdition): ClockSource = when (edition) {
            GameEdition.PrepareToDie -> ClockSource(
                pointer = PTDEGameDataMan.Pointer,
                timeOffset = PTDEGameDataMan.InGameTimeMillis,
                tickHz = edition.logicTickHz
            )
            GameEdition.Remastered -> ClockSource(
                pointer = DSRGameDataMan.Pointer,
                timeOffset = DSRGameDataMan.InGameTimeMillis,
                tickHz = edition.logicTickHz
            )
        }
    }
}
