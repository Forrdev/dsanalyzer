package com.sappyoak.dsanalyzer.app.data

import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

public class IndexQueue {
    private val semaphore = Semaphore(1)

    public suspend fun <T> run (block: suspend () -> T): T = semaphore.withPermit { block() }
}
