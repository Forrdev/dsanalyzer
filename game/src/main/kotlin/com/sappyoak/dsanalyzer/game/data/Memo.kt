package com.sappyoak.dsanalyzer.game.data

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlin.time.measureTimedValue

private val logger = KotlinLogging.logger {  }

internal class Memo<K : Any, V>(
    private val name: String,
    private val scope: CoroutineScope,
    private val capacity: Int? = null
) {
    private val entries = LinkedHashMap<K, Deferred<V>>(16, 0.75f, true)

    suspend fun get(key: K, load: suspend () -> V): V {
        val entry = synchronized(entries) {
            entries[key] ?: scope.async { timed(key, load) }.also { started ->
                entries[key] = started
                trim()
            }
        }

        return try {
            entry.await()
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (err: Throwable) {
            synchronized(entries) { if (entries[key] === entry) entries.remove(key) }
            throw err
        }
    }

    fun clear() = synchronized(entries) { entries.clear() }

    private suspend fun timed(key: K, load: suspend () -> V): V {
        val (value, took) = measureTimedValue { load() }
        logger.debug { "Loaded $name $key in ${took.inWholeMilliseconds}ms" }
        return value
    }

    private fun trim() {
        val limit = capacity ?: return
        val iterator = entries.keys.iterator()
        while (entries.size > limit) {
            val unused = iterator.next()
            iterator.remove()
        }
    }
}
