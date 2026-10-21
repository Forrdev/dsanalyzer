package com.sappyoak.dsanalyzer.runtime.session

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

/** The wait before the first retry, doubled for each one after it */
private val FIRST_WAIT: Duration = 1.seconds

/** One, two, four... thirty-two seconds apart: a minute after attaching, then no more */
private const val ATTEMPTS: Int = 6

internal class SignatureRetry(private val clock: TimeSource = TimeSource.Monotonic) {
    private var attempts = 0
    private var next: TimeMark = clock.markNow() + FIRST_WAIT

    fun due(): Boolean {
        if (attempts >= ATTEMPTS || next.hasNotPassedNow()) return false

        attempts++
        next = clock.markNow() + FIRST_WAIT * (1 shl attempts)
        return true
    }
}
