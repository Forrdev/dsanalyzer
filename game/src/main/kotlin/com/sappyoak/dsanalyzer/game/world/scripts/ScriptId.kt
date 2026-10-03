package com.sappyoak.dsanalyzer.game.world.scripts

import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.label

public sealed interface ScriptId {
    public val label: String

    public data object Common : ScriptId {
        override val label: String = "common"
        override fun toString(): String = label
    }

    public data class Of(public val map: MapId) : ScriptId {
        override val label: String = map.name
        override fun toString(): String = label
    }
}

public val ScriptId.title: String
    get() = when (this) {
        ScriptId.Common -> "Common"
        is ScriptId.Of -> map.label
    }