package com.sappyoak.dsanalyzer.game.world.scripts.index

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

import com.sappyoak.dsanalyzer.game.data.GameData
import com.sappyoak.dsanalyzer.game.world.scripts.ScriptId
import com.sappyoak.dsanalyzer.shared.binary.BinaryFormatException

private val logger = KotlinLogging.logger { }

/**
 * Every event script indexed once: where each flag is used, where each event is started, and which
 * scripts could not be read. Built over the same scripts as a whole, so a caller never has to pair a
 * call index with the scripts it came from
 */
public data class ScriptIndex(
    public val flags: FlagIndex,
    public val calls: EventCallIndex,
    /** Scripts that did not parse, left out of both indexes */
    public val unreadable: List<ScriptId>
) {
    public companion object {
        public val Empty: ScriptIndex = ScriptIndex(FlagIndex.Empty, EventCallIndex.Empty, emptyList())

        /** Merges each script's indexing; a null indexing is a script that could not be read */
        public fun of(indexings: Map<ScriptId, ScriptIndexing?>): ScriptIndex {
            val built = indexings.values.filterNotNull()
            val calls = EventCallIndex.of(
                found = built.flatMap { it.calls },
                needs = built.fold(emptyMap()) { all, one -> all + one.needs },
                scripts = built.size
            )
            return ScriptIndex(
                flags = FlagIndex.of(found = built.flatMap { it.flagUses }, scripts = built.size, calls = calls),
                calls = calls,
                unreadable = indexings.filterValues { it == null }.keys.toList()
            )
        }
    }
}

/** Loads and indexes every script at once; a script that does not parse is logged and left out */
internal suspend fun GameData.indexScripts(): ScriptIndex = coroutineScope {
    val indexings = catalog().scripts.map { script ->
        async(Dispatchers.Default) { script to indexingOf(script) }
    }.awaitAll().toMap()

    ScriptIndex.of(indexings).also { index ->
        logger.atInfo {
            message = "Scripts indexed"
            payload = mapOf(
                "scripts" to index.calls.scripts,
                "unreadable" to index.unreadable.size,
                "callSites" to index.calls.sites,
                "callerArgumentOffset" to index.calls.base
            )
        }
    }
}

private suspend fun GameData.indexingOf(script: ScriptId): ScriptIndexing? = try {
    script(script)?.indexed()
} catch (err: BinaryFormatException) {
    logger.atWarn {
        message = "Script left out of index"
        cause = err
        payload = mapOf("script" to script.label)
    }
    null
}
