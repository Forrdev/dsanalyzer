package com.sappyoak.dsanalyzer.shared.logging

import io.github.oshai.kotlinlogging.KLogger
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.DurationUnit

public const val TIMING_MESSAGE: String = "timing"
public const val TIMING_NAME: String = "timing"
public const val TIMING_MILLIS: String = "ms"

/** Logs that [name] took [took], in milliseconds to the microsecond, with [fields] alongside */
public fun KLogger.timing(name: String, took: Duration, fields: Map<String, Any?> = emptyMap()) {
    atDebug {
        message = TIMING_MESSAGE
        payload = mapOf(TIMING_NAME to name, TIMING_MILLIS to took.millis()) + fields
    }
}

/** Milliseconds with three decimals, the same whatever the machine's locale */
public fun Duration.millis(): String = String.format(Locale.ROOT, "%.3f", toDouble(DurationUnit.MILLISECONDS))
