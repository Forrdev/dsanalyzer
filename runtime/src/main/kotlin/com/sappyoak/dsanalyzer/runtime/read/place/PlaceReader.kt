package com.sappyoak.dsanalyzer.runtime.read.place

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.exists
import com.sappyoak.dsanalyzer.runtime.layout.PlaceLayout
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.snapshot.WorldPlace

/** The map the player is in, or why there is none: a map the game data lacks is still loading */
internal class PlaceReader(private val layout: PlaceLayout) {
    private val view = StructView(layout.pointer)

    fun refresh(memory: GameMemory): Boolean = view.refresh(memory)

    fun place(): WorldPlace {
        if (!view.isPresent) return WorldPlace.Unreadable
        if (layout.noMap?.let { view.int(it.offset) == it.value } == true) return WorldPlace.NoWorld

        val map = MapId.of(area = view.unsigned(layout.area), block = view.unsigned(layout.block))
        return if (map.exists) WorldPlace.InWorld(map) else WorldPlace.Loading(map)
    }
}
