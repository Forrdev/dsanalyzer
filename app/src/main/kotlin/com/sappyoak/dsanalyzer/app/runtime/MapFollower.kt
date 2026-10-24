package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot

internal class MapFollower {
    private var current: MapId? = null

    fun entered(snapshot: RuntimeSnapshot): Boolean {
        val map = snapshot.map?.takeIf { snapshot.inWorld } ?: return false
        val moved = current != null && map != current
        current = map
        return moved
    }

    fun forget() {
        current = null
    }
}
