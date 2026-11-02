package com.sappyoak.dsanalyzer.app.data.esd

import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.esd.EsdDefinitions
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface EsdsMessage {
    public data class Opened(public val installation: Installation) : EsdsMessage
    public data class CatalogLoaded(public val talk: Map<MapId, List<Int>>) : EsdsMessage
    public data class SourceSelected(public val source: EsdSource) : EsdsMessage
    public data class Loaded(
        public val source: EsdSource,
        public val esd: Esd,
        public val definitions: EsdDefinitions
    ) : EsdsMessage
    public data class Failed(public val reason: String) : EsdsMessage
}
