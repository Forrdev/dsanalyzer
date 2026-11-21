package com.sappyoak.dsanalyzer.runtime.sampling

import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.TimeSource

/**
 * Spaces ticks a [period] apart on a fixed clock, so each one is due a period after the last
 * was due, not after it finished. A wait still lands on the system timer's steps, but the
 * late ticks and the early ones even out to the period. Behind by more than a whole period,
 * it starts the clock again rather than running the missed ticks back to back
 */
internal class TickPacer(val period: Duration, private val clock: TimeSource) {
    private var due = clock.markNow()
    suspend fun awaitNext() {
        due += period
        val late = due.elapsedNow()
        when {
            late.isNegative() -> delay(-late)
            late > period -> due = clock.markNow()
        }
    }
}

