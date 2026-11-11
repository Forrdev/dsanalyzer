package com.sappyoak.dsanalyzer.runtime.write.mailbox

/** The most calls or tasks that may wait at one time. Past this a request is refused */
internal const val QUEUE_LIMIT: Int = 256

public sealed interface Submission {
    public data class Queued(public val id: Long) : Submission
    public data class Refused(public val reason: String) : Submission
}
