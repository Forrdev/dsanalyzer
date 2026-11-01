package com.sappyoak.dsanalyzer.runtime.read.place

import com.sappyoak.dsanalyzer.game.world.maps.exists
import com.sappyoak.dsanalyzer.runtime.dsr.WorldInfoOwner
import com.sappyoak.dsanalyzer.runtime.pointers.GameMemory
import com.sappyoak.dsanalyzer.runtime.pointers.StructView
import com.sappyoak.dsanalyzer.runtime.session.WorldPlace

internal class DSRPlaceReader : PlaceSource {
    private val view = StructView(WorldInfoOwner.Pointer)

    override fun refresh(memory: GameMemory): Boolean = view.refresh(memory)

    override fun place(): WorldPlace {
        if (!view.isPresent) return WorldPlace.Unreadable

        val map = WorldInfoOwner.mapOf(view.int(WorldInfoOwner.CurrentMap)) ?: return WorldPlace.NoWorld

        return if (map.exists) WorldPlace.InWorld(map) else WorldPlace.Loading(map)
    }
}
