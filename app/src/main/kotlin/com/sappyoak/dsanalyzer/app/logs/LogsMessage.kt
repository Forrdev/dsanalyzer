package com.sappyoak.dsanalyzer.app.logs

import com.sappyoak.dsanalyzer.app.store.FrequentMessage

public sealed interface LogsMessage {
    public data object Start : LogsMessage

    /** Frequent so the store does not log it: logging it would add a line, and so another batch */
    public data class Read(public val batch: LogBatch) : LogsMessage, FrequentMessage
    public data class Failed(public val reason: String) : LogsMessage

    public data class FilterChanged(public val filter: LogFilter) : LogsMessage
    public data class EntrySelected(public val sequence: Long?) : LogsMessage
    public data class ProblemSelected(public val key: String?) : LogsMessage
}
