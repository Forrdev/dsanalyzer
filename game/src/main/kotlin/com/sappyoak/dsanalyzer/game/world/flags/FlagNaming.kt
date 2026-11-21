package com.sappyoak.dsanalyzer.game.world.flags

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.game.data.GameCatalog
import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.world.ai.LuaFlagSet
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.maps.loadCharacterModels
import com.sappyoak.dsanalyzer.game.world.params.RowNameTranslations
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

/**
 * Names every flag the game data ties to something with a name of its own, and finds every place outside the
 * event scripts that sets or reads one: item lots, shop stock, ObjActs, talk scripts, Lua scripts and the
 * executable itself. [rowNames] names characters the game's text does not, from their NpcParam rows
 */
public suspend fun GameData.readFlags(
    language: TextLanguage,
    rowNames: RowNameTranslations = RowNameTranslations.None
): FlagReading = coroutineScope {
    // Every input loads at once, so the slow ones (params, the script and AI indexes) overlap the rest
    val catalog = catalog()
    val scripts = async { scriptOrigin() }
    val talk = async { TalkOrigin(talkScripts()) }
    val lua = async { LuaOrigin(luaFlagSets()) }
    val context = namingContext(language, catalog, rowNames) ?: return@coroutineScope FlagReading.Empty
    val origins = listOf(LotOrigin, MapOrigin, scripts.await(), talk.await(), lua.await(), EngineOrigin)

    withContext(Dispatchers.Default) { FlagReading.of(origins.map { it.read(context) }) }
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
    val text = async { text(language, NAMING_TEXT) }
    val loaded = params.await() ?: return@coroutineScope null
    NamingContext(loaded, items.await(), text.await(), maps.await(), loadCharacterModels(), rowNames)
}

/** The scripts the index could read, each bound to what its callers hand it */
private suspend fun GameData.scriptOrigin(): ScriptOrigin {
    val index = scriptIndex()
    val readable = catalog().scripts - index.unreadable.toSet()
    val decoded = readable.loadEach { script(it) }
    val names = readable.loadEach { it to scriptNames(it) }.toMap()
    return ScriptOrigin(decoded.flatMap { it.bound(index.calls) }) { names.getValue(it.script) }
}

private suspend fun GameData.talkScripts(): List<TalkScript> = talk().toList().loadEach { (map, ids) ->
    ids.mapNotNull { id -> esd(EsdSource.Talk(map, id))?.let { TalkScript(map, id, it) } }
}.flatten()

/** None when the Lua scripts cannot be indexed, which the AI views report themselves */
private suspend fun GameData.luaFlagSets(): Map<Int, List<LuaFlagSet>> = try {
    aiIndex().flagSets
} catch (_: BinaryFormatException) {
    emptyMap()
}

/** Loads every item at once, leaving out those that come back empty */
private suspend fun <T, R : Any> List<T>.loadEach(load: suspend (T) -> R?): List<R> = coroutineScope {
    map { async { load(it) } }.awaitAll().filterNotNull()
}
