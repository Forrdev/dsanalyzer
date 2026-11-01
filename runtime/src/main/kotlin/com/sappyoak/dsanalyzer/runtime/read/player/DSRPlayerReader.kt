package com.sappyoak.dsanalyzer.runtime.read.player

import com.sappyoak.dsanalyzer.game.GameEdition
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

    override fun frame(): Long = timeMillis().toLong() * GameEdition.Remastered.logicTickHz / 1000

    override fun player(): PlayerSnapshot? = null

    override fun world(): WorldSnapshot? = null
}

