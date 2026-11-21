package com.sappyoak.dsanalyzer.app.data

import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

/** More than this and the indexes mostly fight over the disk and the heap */
private const val MOST_AT_ONCE = 3

/**
 * Bounds how many whole-installation indexes (scripts, flags, AI, animation sets, map entities) run at once,
 * so the ones asked for first are not all left waiting behind one
 */
public class IndexQueue(permits: Int = defaultPermits()) {
    private val semaphore = Semaphore(permits)

    public suspend fun <T> run(block: suspend () -> T): T = semaphore.withPermit { block() }
}

private fun defaultPermits(): Int = (Runtime.getRuntime().availableProcessors() / 2).coerceIn(1, MOST_AT_ONCE)
