package com.sappyoak.dsanalyzer.app.data

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.shared.logging.millis
import com.sappyoak.dsanalyzer.shared.logging.timing

private val logger = KotlinLogging.logger { }

/** More than this and the indexes mostly fight over the disk and the heap */
private const val MOST_AT_ONCE = 3

/**
 * Bounds how many whole-installation indexes (scripts, flags, AI, animation sets, map entities) run at once,
 * so the ones asked for first are not all left waiting behind one. Each run is timed under its [name], with
 * how long it waited for its turn alongside
 */
public class IndexQueue(permits: Int = defaultPermits()) {
    private val semaphore = Semaphore(permits)

    public suspend fun <T> run(name: String, block: suspend () -> T): T {
        val asked = TimeSource.Monotonic.markNow()
        return semaphore.withPermit {
            val waited = asked.elapsedNow()
            val started = TimeSource.Monotonic.markNow()
            block().also {
                logger.timing("index $name", started.elapsedNow(), mapOf("waitedMs" to waited.millis()))
            }
        }
    }
}

private fun defaultPermits(): Int = (Runtime.getRuntime().availableProcessors() / 2).coerceIn(1, MOST_AT_ONCE)
