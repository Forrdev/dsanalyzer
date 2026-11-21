package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess

/**
 * One kind of game data that says something about flags: item lots, maps, event scripts, talk
 * scripts, Lua or the executable. Each owns its own rules and wording; [readFlags] only gathers them
 */
internal fun interface FlagOrigin {
    fun read(context: NamingContext): OriginReading
}

/** What one origin found: readings of what flags mean, and places that set or read them */
internal data class OriginReading(
    val names: List<Pair<Int, FlagEvidence>> = emptyList(),
    val sites: List<Pair<Int, FlagSite>> = emptyList()
)

/** What the game data says of flags beside the event scripts: what each means, and where else it is used */
public data class FlagReading(public val names: FlagNames, public val sites: FlagSites) {
    public companion object {
        public val Empty: FlagReading = FlagReading(FlagNames.Empty, FlagSites.Empty)

        /** Every origin's findings together, each flag's readings ranked and each site listed once */
        internal fun of(readings: List<OriginReading>): FlagReading = FlagReading(
            names = FlagNames.of(readings.flatMap { it.names }),
            sites = FlagSites.of(readings.flatMap { it.sites })
        )
    }
}

/** A reading taken straight from a row or an entry is also where the game sets the flag */
internal fun List<Pair<Int, FlagEvidence>>.withSites(): OriginReading = OriginReading(
    names = this,
    sites = map { (flagId, evidence) ->
        flagId to FlagSite(FlagAccess.Writes, evidence.label, FlagSource.World(evidence.source))
    }
)
