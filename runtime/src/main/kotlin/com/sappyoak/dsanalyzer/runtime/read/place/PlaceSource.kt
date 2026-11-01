package com.sappyoak.dsanalyzer.runtime.read.place

import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace

internal interface PlaceSource {
    fun refresh(memory: GameMemory): Boolean

    fun place(): WorldPlace
}
