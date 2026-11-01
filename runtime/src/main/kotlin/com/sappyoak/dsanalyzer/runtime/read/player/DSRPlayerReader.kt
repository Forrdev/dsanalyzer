package com.sappyoak.dsanalyzer.runtime.read.player

import com.sappyoak.dsanalyzer.runtime.dsr.GameDataMan
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot

internal class DSRPlayerReader : PlayerSource {
    private val gameData = StructView(GameDataMan.Pointer)

    override val providesSnapshot: Boolean get() = false

    override val structures: Int get() = 1

    override fun refresh(memory: GameMemory): Int = if (gameData.refresh(memory)) 1 else 0

    override fun timeMillis(): Int =
        if (gameData.isPresent) gameData.int(GameDataMan.InGameTimeMillis) else 0

    override fun frame(): Long = timeMillis().toLong() * TICK_HZ / 1000

    override fun player(): PlayerSnapshot? = null

    override fun world(): WorldSnapshot? = null
}

/**
 * Logic ticks per second, **assumed equal to Prepare to Die's and not verified**
 *
 * Remastered uncapped its render rate, and whether its logic tick moved with it has not been
 * checked. If it did, every frame number this reports is out by that ratio. It is a named constant
 * in one place so the correction is a one-line change once somebody measures it; until then
 * [DsrPlayerReader.timeMillis] is the figure to trust
 */
private const val TICK_HZ: Int = 30
