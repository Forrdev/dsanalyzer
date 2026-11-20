package com.sappyoak.dsanalyzer.app.data.flags

import com.sappyoak.dsanalyzer.game.world.flags.FlagKind
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.maps.label

/** A way of listing flags in the navigator */
public sealed interface FlagGroup {
    public val label: String

    public data object All : FlagGroup {
        override val label: String = "All flags"
    }

    /** Flags whose best reading is of [kind] */
    public data class Kind(public val kind: FlagKind) : FlagGroup {
        override val label: String get() = kind.label
    }

    /** Flags the scripts use but nothing names */
    public data object Unnamed : FlagGroup {
        override val label: String = "Unnamed"
    }

    /** Flags whose id places them in [map] */
    public data class Map(public val map: MapId) : FlagGroup {
        override val label: String get() = map.label
    }
}

private val MAP_FLAGS = 10_000_000..19_999_999

internal fun mapOfFlag(flagId: Int): MapId? {
    if (flagId !in MAP_FLAGS) return null
    return MapId.of(area = flagId / 100_000 % 100, block = flagId / 10_000 % 10)
}
