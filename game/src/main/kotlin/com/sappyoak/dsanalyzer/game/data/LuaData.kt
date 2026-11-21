package com.sappyoak.dsanalyzer.game.data

import java.nio.file.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.world.ai.AiIndex
import com.sappyoak.dsanalyzer.game.world.ai.LuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.LuaCache
import com.sappyoak.dsanalyzer.game.world.ai.LuaCacheStamp
import com.sappyoak.dsanalyzer.game.world.ai.LuaScriptId
import com.sappyoak.dsanalyzer.game.world.ai.LuaSource
import com.sappyoak.dsanalyzer.game.world.ai.availableLua
import com.sappyoak.dsanalyzer.game.world.ai.decompileScript
import com.sappyoak.dsanalyzer.game.world.ai.indexAi
import com.sappyoak.dsanalyzer.game.world.ai.loadLua
import com.sappyoak.dsanalyzer.game.world.ai.loadLuaBinder
import com.sappyoak.dsanalyzer.game.world.ai.loadLuaInfo
import com.sappyoak.dsanalyzer.game.world.maps.MapId

private const val KEPT_LUA = 8

/** The directory under an installation's own where its Lua is cached */
private const val CACHE_DIRECTORY = "lua"

/**
 * An installation's Lua, read through the on-disk [LuaCache] in [installationDirectory] when there is one.
 * Building the AI index decompiles every AI script, so it fills the cache with their source on the way
 */
internal class LuaData(
    private val installation: Installation,
    private val files: InstallationFiles,
    private val installationDirectory: Path?,
    private val maps: suspend () -> List<MapId>,
    scope: CoroutineScope
) {
    private val caches = Memo<Unit, LuaCache?>("lua cache", scope)
    private val catalog = Memo<Unit, Map<LuaBinder, List<String>>>("lua catalog", scope)
    private val sources = Memo<LuaScriptId, LuaSource?>("lua", scope, KEPT_LUA)
    private val aiIndexes = Memo<Unit, AiIndex>("ai index", scope)

    suspend fun scripts(): Map<LuaBinder, List<String>> = catalog.get(Unit) {
        cached({ it.catalog() }, { cache, value -> cache.saveCatalog(value) }) {
            val maps = maps()
            parse { it.availableLua(maps) }
        }
    }

    suspend fun source(script: LuaScriptId): LuaSource? = sources.get(script) {
        cached({ it.source(script) }, { cache, value -> cache.saveSource(script, value) }) {
            parse { game -> game.loadLua(script)?.let { LuaSource.of(decompileScript(it)) } }
        }
    }

    /** Goals and logic across every AI binder; the event binder registers none */
    suspend fun aiIndex(): AiIndex = aiIndexes.get(Unit) {
        cached({ it.aiIndex() }, { cache, value -> cache.saveAiIndex(value) }) {
            val binders = scripts().keys - LuaBinder.Event
            val cache = cache()
            parse { game ->
                val mains = binders.flatMap { binder ->
                    game.loadLuaBinder(binder).map { (name, chunk) ->
                        val script = LuaScriptId(binder, name)
                        val decompiled = decompileScript(chunk)
                        cache?.saveSource(script, LuaSource.of(decompiled))
                        script to decompiled.main
                    }
                }
                val infos = binders.mapNotNull { binder -> game.loadLuaInfo(binder)?.let { binder to it } }
                indexAi(mains.toMap(), infos.toMap())
            }
        }
    }

    fun clear() = listOf(caches, catalog, sources, aiIndexes).forEach(Memo<*, *>::clear)

    private suspend fun cache(): LuaCache? = caches.get(Unit) {
        installationDirectory?.let { io { LuaCache.open(it.resolve(CACHE_DIRECTORY), LuaCacheStamp.of(installation)) } }
    }

    /** What [read] finds in the cache, or else [compute]'s result, which [save] then keeps unless it is null */
    private suspend fun <T> cached(
        read: (LuaCache) -> T?,
        save: (LuaCache, T & Any) -> Unit,
        compute: suspend () -> T
    ): T {
        val cache = cache()
        cache?.let { io { read(it) } }?.let { return it }
        val value = compute()
        if (value != null && cache != null) io { save(cache, value) }
        return value
    }

    private suspend fun <T> io(block: () -> T): T = withContext(Dispatchers.IO) { block() }

    private suspend fun <T> parse(block: (GameFiles) -> T): T =
        files.use(installation) { game -> withContext(Dispatchers.Default) { block(game) } }
}
