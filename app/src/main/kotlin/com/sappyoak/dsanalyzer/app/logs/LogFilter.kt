package com.sappyoak.dsanalyzer.app.logs

/**
 * Which lines the log list shows; [text] matches the message, the logger and the fields. [thisLaunch]
 * keeps to the latest launch of the tool, for the problems and timings too, since the file holds the day's
 */
public data class LogFilter(
    public val thisLaunch: Boolean = true,
    public val level: LogLevel = LogLevel.Info,
    public val hiddenAreas: Set<String> = emptySet(),
    public val text: String = ""
) {
    public fun shows(entry: LogEntry): Boolean =
        entry.level >= level && entry.area !in hiddenAreas && (text.isEmpty() || entry.mentions(text))

    public fun toggled(area: String): LogFilter =
        copy(hiddenAreas = if (area in hiddenAreas) hiddenAreas - area else hiddenAreas + area)
}

private fun LogEntry.mentions(text: String): Boolean =
    message.contains(text, ignoreCase = true) ||
            logger.contains(text, ignoreCase = true) ||
            fields.values.any { it.contains(text, ignoreCase = true) } ||
            error?.type?.contains(text, ignoreCase = true) == true
