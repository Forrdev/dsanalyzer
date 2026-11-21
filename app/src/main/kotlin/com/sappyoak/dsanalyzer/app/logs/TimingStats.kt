package com.sappyoak.dsanalyzer.app.logs

import com.sappyoak.dsanalyzer.shared.logging.TIMING_MESSAGE
import com.sappyoak.dsanalyzer.shared.logging.TIMING_MILLIS
import com.sappyoak.dsanalyzer.shared.logging.TIMING_NAME

/** Every timing logged under one name, in milliseconds */
public data class TimingStat(
    public val name: String,
    public val count: Int,
    public val totalMillis: Double,
    public val longestMillis: Double,
    public val lastMillis: Double
) {
    public val averageMillis: Double get() = if (count == 0) 0.0 else totalMillis / count
}

/** The timings among [entries], gathered by name */
public fun gatherTimings(entries: List<LogEntry>): Map<String, TimingStat> {
    val gathered = mutableMapOf<String, TimingStat>()
    entries.filter { it.message == TIMING_MESSAGE }.forEach { entry ->
        val name = entry.fields[TIMING_NAME] ?: return@forEach
        val millis = entry.fields[TIMING_MILLIS]?.toDoubleOrNull() ?: return@forEach
        val known = gathered[name]
        gathered[name] = known?.copy(
            count = known.count + 1,
            totalMillis = known.totalMillis + millis,
            longestMillis = maxOf(known.longestMillis, millis),
            lastMillis = millis
        ) ?: TimingStat(name, 1, millis, millis, millis)
    }
    return gathered
}
