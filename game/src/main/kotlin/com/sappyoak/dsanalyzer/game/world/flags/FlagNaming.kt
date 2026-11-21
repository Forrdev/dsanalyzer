package com.sappyoak.dsanalyzer.game.world.flags

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.formats.emevd.emedf.FlagAccess

import com.sappyoak.dsanalyzer.game.data.DecodedScript
import com.sappyoak.dsanalyzer.game.data.GameCatalog
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
): FlagReading = coroutineScope {
    // Every source loads at once, so the slow ones (params, the AI index) overlap the rest
    val catalog = catalog()
    val scripts = async { catalog.scripts.loadEach { readable(it) } }
    val names = async { catalog.scripts.loadEach { it to scriptNames(it) }.toMap() }
    val talk = async { talkScripts() }
    val lua = async { luaFlagSites() }
    val context = namingContext(language, catalog, rowNames) ?: return@coroutineScope FlagReading.Empty
    val eventNames = names.await()
    val bound = scripts.await()

    withContext(Dispatchers.Default) {
        val placed = context.lotFlags() + context.mapFlags()
        val fromScripts = context.scriptFlags(bound.flatMap { it.bound(calls) }) { eventNames.getValue(it.script) }
        val talking = talk.await()
        FlagReading(
            names = FlagNames.of(placed + fromScripts + context.talkFlags(talking)),
            sites = FlagSites.of(
                placed.map { it.asSite() } + context.talkSites(talking) + lua.await() + engineFlagSites()
            )
        )
    }
}

/** Null without params, since nothing can be named without them */
private suspend fun GameData.namingContext(
    language: TextLanguage,
    catalog: GameCatalog,
    rowNames: RowNameTranslations
): NamingContext? = coroutineScope {
    val params = async { params() }
    val items = async { paramNames(language) }
    val maps = async { catalog.maps.loadEach { map -> msb(map)?.let { map to it } }.toMap() }
    val text = async { read { it.loadText(language, NAMING_TEXT) } }
    val loaded = params.await() ?: return@coroutineScope null
    NamingContext(loaded, items.await(), text.await(), maps.await(), loadCharacterModels(), rowNames)
}

private suspend fun GameData.talkScripts(): List<TalkScript> = talk().toList().loadEach { (map, ids) ->
    ids.mapNotNull { id -> esd(EsdSource.Talk(map, id))?.let { TalkScript(map, id, it) } }
}.flatten()

/** Loads every item at once, leaving out those that come back empty */
private suspend fun <T, R : Any> List<T>.loadEach(load: suspend (T) -> R?): List<R> = coroutineScope {
    map { async { load(it) } }.awaitAll().filterNotNull()
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
