package com.sappyoak.dsanalyzer.app.data.esd

import com.sappyoak.dsanalyzer.formats.esd.Esd
import com.sappyoak.dsanalyzer.formats.esd.EsdDefinitions
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public data class EsdsState(
    public val installation: Installation? = null,
    public val talk: Map<MapId, List<Int>> = emptyMap(),
    public val shown: EsdSource? = null,
    public val esd: Esd? = null,
    public val definitions: EsdDefinitions = EsdDefinitions.None,
    public val loading: Boolean = false,
    public val problem: String? = null
) {
    public val sources: List<EsdSource> by lazy {
        listOf(EsdSource.Player) + talk.flatMap { (map, ids) -> ids.map { EsdSource.Talk(map, it) } }
    }

    public fun has(source: EsdSource): Boolean = when (source) {
        EsdSource.Player -> installation != null
        is EsdSource.Talk -> talk[source.map]?.contains(source.talkId) == true
    }
}
