package com.sappyoak.dsanalyzer.runtime.read.player

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.PlayerSnapshot
import com.sappyoak.dsanalyzer.runtime.session.WorldSnapshot

internal interface PlayerSource {
    val providesSnapshot: Boolean
    val structures: Int

    fun refresh(memory: GameMemory): Int
    fun timeMillis(): Int
    fun frame(): Long
    fun player(memory: GameMemory): PlayerSnapshot?
    fun world(): WorldSnapshot?
}
