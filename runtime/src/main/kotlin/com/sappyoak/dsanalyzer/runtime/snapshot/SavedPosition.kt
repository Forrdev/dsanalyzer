package com.sappyoak.dsanalyzer.runtime.snapshot

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.shared.math.Vec3

public data class SavedPosition(
    public val map: MapId,
    public val position: Vec3,
    public val angle: Float
) {
    public companion object {
        public fun of(snapshot: RuntimeSnapshot): SavedPosition? {
            val map = snapshot.place.loadedMap ?: return null
            val player = snapshot.player ?: return null
            return SavedPosition(map, player.position, player.angle)
        }
    }
}
