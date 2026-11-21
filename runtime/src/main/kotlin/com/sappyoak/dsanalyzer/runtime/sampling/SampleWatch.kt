package com.sappyoak.dsanalyzer.runtime.sampling

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.shared.logging.millis
import com.sappyoak.dsanalyzer.shared.logging.timing

private val logger = KotlinLogging.logger { }

/** Long enough that a steady game adds a line now and then rather than one a tick */
private val REPORT_EVERY = 30.seconds

/** One late tick in this many is a pause now and then (a collection, the scheduler), not falling behind */
private const val LATE_SHARE = 20

/** A single tick this many periods long is worth a warning however rare */
private const val STALL_PERIODS = 4

/**
 * Keeps the sampling loop's cost out of the log until it matters: a timing of the average tick every
 * [REPORT_EVERY], and a warning with it when ticks keep running past the [period] they are meant to fit
 * in, or one stalls for several periods
 */
internal class SampleWatch(private val period: Duration) {
    private var since = TimeSource.Monotonic.markNow()
    private var ticks = 0
    private var total = Duration.ZERO
    private var longest = Duration.ZERO
    private var overran = 0

    fun record(took: Duration) {
        ticks++
        total += took
        if (took > longest) longest = took
        if (took > period) overran++
        if (since.elapsedNow() >= REPORT_EVERY) report()
    }

    private fun report() {
        logger.timing("sample tick", total / ticks, mapOf("ticks" to ticks, "longestMs" to longest.millis()))
        if (overran * LATE_SHARE >= ticks || longest > period * STALL_PERIODS) logger.atWarn {
            message = "Samples are running past their period"
            payload = mapOf(
                "overran" to overran,
                "ticks" to ticks,
                "periodMs" to period.inWholeMilliseconds,
                "longestMs" to longest.millis()
            )
        }
        since = TimeSource.Monotonic.markNow()
        ticks = 0
        total = Duration.ZERO
        longest = Duration.ZERO
        overran = 0
    }
}
