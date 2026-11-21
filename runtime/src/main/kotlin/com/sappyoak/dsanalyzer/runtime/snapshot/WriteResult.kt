package com.sappyoak.dsanalyzer.runtime.snapshot

/** What became of one submitted write, reported once, in the first snapshot after it settles */
public data class WriteResult(
    /** The number [com.sappyoak.dsanalyzer.runtime.write.request.WriteRequests.submit] gave back */
    public val ticket: Long,
    public val label: String,
    public val outcome: WriteOutcome
)

public sealed interface WriteOutcome {
    /** [value] is what the write produced, where it produces one: a flag's state, a slot, a count */
    public data class Done(public val value: Long = 0) : WriteOutcome
    public data class Failed(public val reason: String) : WriteOutcome
}
