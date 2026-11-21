package com.sappyoak.dsanalyzer.app.logs

import java.time.Instant

/**
 * Warnings and errors that are the same problem, gathered into one: same logger, same exception type and
 * the same place in our own code. Without an exception the message stands in for the place
 */
public data class Problem(
    public val key: String,
    public val level: LogLevel,
    public val logger: String,
    public val count: Int,
    public val first: Instant,
    /** The latest occurrence, whose message, error and context the problem shows */
    public val latest: LogEntry
)

public fun LogEntry.problemKey(): String = listOf(
    logger,
    error?.type.orEmpty(),
    error?.origin?.let { "${it.className}.${it.method}" } ?: message
).joinToString("|")

/** The problems among [entries]; anything below a warning is left out */
public fun gatherProblems(entries: List<LogEntry>): Map<String, Problem> {
    val gathered = mutableMapOf<String, Problem>()
    entries.filter { it.level >= LogLevel.Warn }.forEach { entry ->
        val key = entry.problemKey()
        val known = gathered[key]
        gathered[key] = known?.copy(
            level = maxOf(known.level, entry.level),
            count = known.count + 1,
            latest = entry
        ) ?: Problem(key, entry.level, entry.logger, count = 1, first = entry.time, latest = entry)
    }
    return gathered
}
