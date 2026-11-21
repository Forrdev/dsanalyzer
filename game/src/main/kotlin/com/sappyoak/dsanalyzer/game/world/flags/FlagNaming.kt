package com.sappyoak.dsanalyzer.game.world.flags

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess

import com.sappyoak.dsanalyzer.game.data.DecodedScript
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.game.world.scripts.index.EventCallIndex
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.game.world.text.loadText
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

/**
 * Names every flag the game data ties to something with a name of its own, and finds every place outside the
 * event scripts that sets or reads one: item lots, shop stock, ObjActs, talk scripts, Lua scripts and the
 * executable itself. [calls] fills in what common events are handed, so it should be the index built over
 * the same scripts. [rowNames] names characters the game's text does not, from their NpcParam rows
 */
public suspend fun GameData.readFlags(
    language: TextLanguage,
    calls: EventCallIndex,
    rowNames: RowNameTranslations = RowNameTranslations.None
): FlagReading {
    val params = params() ?: return FlagReading.Empty
    val catalog = catalog()
    val maps = catalog.maps.mapNotNull { map -> msb(map)?.let { map to it } }.toMap()
    val text = read { it.loadText(language, NAMING_TEXT) }
    val scripts = catalog.scripts.mapNotNull { readable(it) }
    val names = catalog.scripts.associateWith { scriptNames(it) }
    val talk = talk().flatMap { (map, ids) ->
        ids.mapNotNull { id -> esd(EsdSource.Talk(map, id))?.let { TalkScript(map, id, it) } }
    }
    val lua = luaFlagSites()

    return withContext(Dispatchers.Default) {
        val context = NamingContext(params, paramNames(language), text, maps, loadCharacterModels(), rowNames)
        val bound = scripts.flatMap { it.bound(calls) }
        val placed = context.lotFlags() + context.mapFlags()
        val fromScripts = context.scriptFlags(bound) { names.getValue(it.script) }
        FlagReading(
            names = FlagNames.of(placed + fromScripts + context.talkFlags(talk)),
            sites = FlagSites.of(placed.map { it.asSite() } + context.talkSites(talk) + lua + engineFlagSites())
        )
    }
}

/** The flags Lua scripts set; none when the scripts cannot be indexed, which the AI views report themselves */
private suspend fun GameData.luaFlagSites(): List<Pair<Int, FlagSite>> = try {
    aiIndex().flagSets.flatMap { (flagId, sets) ->
        sets.map { set ->
            val change = when (set.on) {
                true -> "turns it on"
                false -> "turns it off"
                null -> "changes it"
            }
            flagId to FlagSite(FlagAccess.Writes, change, FlagSource.Lua(set.script, set.function))
        }
    }
} catch (_: BinaryFormatException) {
    emptyList()
}

/** A script that does not parse names nothing rather than failing the rest */
private suspend fun GameData.readable(script: ScriptId): DecodedScript? = try {
    script(script)
} catch (_: BinaryFormatException) {
    null
}
