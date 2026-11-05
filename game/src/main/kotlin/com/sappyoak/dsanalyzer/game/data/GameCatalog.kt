package com.sappyoak.dsanalyzer.game.data

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId

public data class GameCatalog(
    public val maps: List<MapId>,
    public val scripts: List<ScriptId>
)
