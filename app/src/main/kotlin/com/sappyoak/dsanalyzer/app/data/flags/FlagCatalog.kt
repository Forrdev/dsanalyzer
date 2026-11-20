package com.sappyoak.dsanalyzer.app.data.flags

import com.sappyoak.dsanalyzer.game.world.flags.FlagKind
import com.sappyoak.dsanalyzer.game.world.flags.FlagNames
import com.sappyoak.dsanalyzer.game.world.maps.MapId
import com.sappyoak.dsanalyzer.game.world.scripts.index.FlagIndex

/** Every known flag, by the groups the navigator lists, each group's ids in order */
public class FlagCatalog private constructor(private val members: Map<FlagGroup, List<Int>>) {
    public val size: Int get() = members[FlagGroup.All].orEmpty().size

    /** Groups that hold any flag: all, then kinds, then unnamed, then maps */
    public val groups: List<FlagGroup> get() = members.keys.toList()

    public operator fun get(group: FlagGroup): List<Int> = members[group].orEmpty()

    public companion object {
        public val Empty: FlagCatalog = FlagCatalog(emptyMap())

        /** Flags the scripts use plus every flag the data names, so read-only and data-only flags both appear */
        public fun of(names: FlagNames, index: FlagIndex): FlagCatalog {
            val ids = (index.flagIds + names.all().map { it.flagId }).sorted()
            if (ids.isEmpty()) return Empty

            val kinds = FlagKind.entries.associate { kind -> FlagGroup.Kind(kind) as FlagGroup to mutableListOf<Int>() }
            val unnamed = mutableListOf<Int>()
            val maps = sortedMapOf<Int, MutableList<Int>>()
            for (id in ids) {
                names[id]?.let { kinds.getValue(FlagGroup.Kind(it.kind)) += id } ?: unnamed.add(id)
                mapOfFlag(id)?.let { maps.getOrPut(it.packed) { mutableListOf() } += id }
            }

            val groups = linkedMapOf<FlagGroup, List<Int>>(FlagGroup.All to ids)
            kinds.filterValues { it.isNotEmpty() }.forEach { (group, members) -> groups[group] = members }
            if (unnamed.isNotEmpty()) groups[FlagGroup.Unnamed] = unnamed
            maps.forEach { (packed, members) -> groups[FlagGroup.Map(MapId(packed))] = members }
            return FlagCatalog(groups)
        }
    }
}
