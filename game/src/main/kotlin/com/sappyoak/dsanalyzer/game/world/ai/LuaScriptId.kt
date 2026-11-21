package com.sappyoak.dsanalyzer.game.world.ai

import com.sappyoak.dsanalyzer.game.files.GamePath
import com.sappyoak.dsanalyzer.game.world.maps.MapId

public sealed interface LuaBinder {
    /** The name the binder goes by, which is also its file name */
    public val label: String

    public data object Common : LuaBinder {
        override val label: String = "aiCommon"
    }

    public data object Event : LuaBinder {
        override val label: String = "eventCommon"
    }

    public data class Map(public val map: MapId) : LuaBinder {
        override val label: String get() = map.toString()
    }

    public companion object {
        /** The binder a [label] names, among [Common], [Event] and the map binders */
        public fun of(label: String): LuaBinder? = when (label) {
            Common.label -> Common
            Event.label -> Event
            else -> MapId.parse(label)?.let(::Map)
        }
    }
}

public val LuaBinder.path: GamePath get() = GamePath.of("/script/$label.luabnd.dcx")

/** A single `.lua` file in a binder, by its bare file name */
public data class LuaScriptId(public val binder: LuaBinder, public val name: String) {
    override fun toString(): String = "${binder.label}/$name"
}
