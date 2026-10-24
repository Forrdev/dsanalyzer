package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.runtime.timing.ModelTimings
import com.sappyoak.dsanalyzer.app.store.FrequentMessage
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface RuntimeMessage {
    public data class Opened(public val installation: Installation) : RuntimeMessage
    public data class LinkChanged(public val link: LinkState) : RuntimeMessage, FrequentMessage

    public data object PlacedEnemiesRequested : RuntimeMessage
    public data object CollisionRequested : RuntimeMessage

    public data class TimingsWanted(public val wanted: Boolean) : RuntimeMessage
    public data class TimingsLoaded(
        public val models: Map<Int, ModelTimings>,
        public val map: MapId?
    ) : RuntimeMessage
}
