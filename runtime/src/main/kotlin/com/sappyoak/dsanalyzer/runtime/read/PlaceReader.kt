package com.sappyoak.dsanalyzer.runtime.read

import com.sappyoak.dsanalyzer.game.world.maps.exists
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.ptde.WorldArea
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace

internal class PlaceReader {
    private val view = StructView(WorldArea.Pointer)

    fun refresh(memory: GameMemory): Boolean = view.refresh(memory)

    fun place(): WorldPlace {
        if (!view.isPresent) return WorldPlace.Unreadable

        val map = WorldArea.mapId(
            world = view.unsigned(WorldArea.World),
            area = view.unsigned(WorldArea.Area)
        )

        return if (map.exists) WorldPlace.InWorld(map) else WorldPlace.Loading(map)
    }
}