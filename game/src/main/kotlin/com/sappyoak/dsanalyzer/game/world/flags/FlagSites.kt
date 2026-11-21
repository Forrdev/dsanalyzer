package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.ref.WorldRef
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource

/** Where outside the event scripts a flag is set or read */
public sealed interface FlagSource {
    /** A param row or map entry: an item lot, a shop's stock, an ObjAct */
    public data class World(public val ref: WorldRef) : FlagSource

    /** One state of an NPC's talk script */
    public data class Talk(
        public val source: EsdSource.Talk,
        public val group: Long,
        public val state: Long
    ) : FlagSource

    /** A function of a Lua script, or its main chunk when [function] is null */
    public data class Lua(public val script: LuaScriptId, public val function: String?) : FlagSource

    /** A function of the executable, by its name and address in Prepare to Die's */
    public data class Engine(public val function: String, public val address: Long) : FlagSource
}

/** One place outside the event scripts that sets or reads a flag; [label] says what it does there */
public data class FlagSite(public val access: FlagAccess, public val label: String, public val source: FlagSource)

/** Every flag use outside the event scripts, by flag */
public class FlagSites private constructor(private val byFlag: Map<Int, List<FlagSite>>) {
    public val flagIds: Set<Int> get() = byFlag.keys

    public operator fun get(flagId: Int): List<FlagSite> = byFlag[flagId].orEmpty()

    public companion object {
        public val Empty: FlagSites = FlagSites(emptyMap())

        public fun of(found: List<Pair<Int, FlagSite>>): FlagSites =
            FlagSites(found.groupBy({ it.first }, { it.second }).mapValues { (_, sites) -> sites.distinct() })
    }
}
