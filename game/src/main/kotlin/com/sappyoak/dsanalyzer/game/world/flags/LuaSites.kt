package com.sappyoak.dsanalyzer.game.world.flags

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess
import com.sappyoak.dsanalyzer.game.world.ai.LuaFlagSet

/** The flags AI scripts set, as places that write them; they name nothing */
internal class LuaOrigin(private val sets: Map<Int, List<LuaFlagSet>>) : FlagOrigin {
    override fun read(context: NamingContext): OriginReading = OriginReading(
        sites = sets.flatMap { (flagId, found) -> found.map { flagId to it.asSite() } }
    )
}

private fun LuaFlagSet.asSite(): FlagSite {
    val change = when (on) {
        true -> "turns it on"
        false -> "turns it off"
        null -> "changes it"
    }
    return FlagSite(FlagAccess.Writes, change, FlagSource.Lua(script, function))
}
