package com.sappyoak.dsanalyzer.app.logs

/** More than this and the oldest lines leave the list, and the problems and timings with them */
internal const val KEPT_ENTRIES = 20_000

public data class LogsState(
    public val entries: List<LogEntry> = emptyList(),
    public val filter: LogFilter = LogFilter(),
    public val selected: Long? = null,
    public val selectedProblem: String? = null,
    /** Why the file could not be read on the latest try; it is tried again every poll */
    public val failure: String? = null
) {
    /** The entries of the latest launch, or every entry kept when the filter reaches past it */
    public val scoped: List<LogEntry> by lazy {
        val start = entries.lastOrNull { it.launch }?.sequence
        if (filter.thisLaunch && start != null) entries.filter { it.sequence >= start } else entries
    }

    public val visible: List<LogEntry> by lazy { scoped.filter(filter::shows) }

    public val areas: List<String> by lazy { entries.map { it.area }.distinct().sorted() }

    public val problems: Map<String, Problem> by lazy { gatherProblems(scoped) }

    public val timings: Map<String, TimingStat> by lazy { gatherTimings(scoped) }

    /** Worst first, then the most frequent */
    public val ranked: List<Problem> by lazy {
        problems.values.sortedWith(compareByDescending<Problem> { it.level }.thenByDescending { it.count })
    }

    /** Slowest on average first */
    public val slowest: List<TimingStat> by lazy { timings.values.sortedByDescending { it.averageMillis } }

    public val shownEntry: LogEntry? get() = selected?.let { at -> entries.firstOrNull { it.sequence == at } }
}
