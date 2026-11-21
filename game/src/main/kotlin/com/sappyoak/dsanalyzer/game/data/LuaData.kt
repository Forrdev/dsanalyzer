package com.sappyoak.dsanalyzer.game.data

import java.nio.file.Path

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
    private val data: DataScope,
    private val installationDirectory: Path?,
    private val maps: suspend () -> List<MapId>
) {
    private val caches = data.memo<Unit, LuaCache?>("lua cache")
    private val catalog = data.memo<Unit, Map<LuaBinder, List<String>>>("lua catalog")
    private val sources = data.memo<LuaScriptId, LuaSource?>("lua", KEPT_LUA)
    private val aiIndexes = data.memo<Unit, AiIndex>("ai index")

    suspend fun scripts(): Map<LuaBinder, List<String>> = catalog.get(Unit) {
        cached({ it.catalog() }, { cache, value -> cache.saveCatalog(value) }) {
            val maps = maps()
            data.parse { it.availableLua(maps) }
        }
    }

    suspend fun source(script: LuaScriptId): LuaSource? = sources.get(script) {
        cached({ it.source(script) }, { cache, value -> cache.saveSource(script, value) }) {
            data.parse { game -> game.loadLua(script)?.let { LuaSource.of(decompileScript(it)) } }
        }
    }

    /** Goals and logic across every AI binder, and every script's flags; the event binder registers no goals */
    suspend fun aiIndex(): AiIndex = aiIndexes.get(Unit) {
        cached({ it.aiIndex() }, { cache, value -> cache.saveAiIndex(value) }) {
            val binders = scripts().keys
            val cache = cache()
            data.parse { game ->
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

    private suspend fun cache(): LuaCache? = caches.get(Unit) {
        val directory = installationDirectory?.resolve(CACHE_DIRECTORY) ?: return@get null
        data.io { LuaCache.open(directory, LuaCacheStamp.of(data.installation)) }
    }

    /** What [read] finds in the cache, or else [compute]'s result, which [save] then keeps unless it is null */
    private suspend fun <T> cached(
        read: (LuaCache) -> T?,
        save: (LuaCache, T & Any) -> Unit,
        compute: suspend () -> T
    ): T {
        val cache = cache()
        cache?.let { data.io { read(it) } }?.let { return it }
        val value = compute()
        if (value != null && cache != null) data.io { save(cache, value) }
        return value
    }
}
