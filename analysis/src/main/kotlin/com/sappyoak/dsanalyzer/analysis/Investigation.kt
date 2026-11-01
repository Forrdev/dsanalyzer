package com.sappyoak.dsanalyzer.analysis

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@JvmInline
@Serializable
public value class InvestigationId(public val value: String) {
    override fun toString(): String = value
}


/***
 * A thread of work, holding the findings it produced.
 */
@Serializable
public data class Investigation(
    public val id: InvestigationId,
    public val title: String,
    public val notes: String = "",
    public val findings: List<Finding> = emptyList(),
    @Contextual public val created: Instant,
    @Contextual public val updated: Instant
) {
    init {
        require(title.isNotBlank()) { "An investigation needs a title; '$id' has none" }
        require(updated >= created) { "'$id' was updated before it was created" }

        val ids = findings.mapTo(mutableSetOf()) { it.id }
        require(ids.size == findings.size) {
            "Two findings in '$id' share an id, which would let one silently shadow the other"
        }
    }

    public fun finding(findingId: FindingId): Finding? = findings.firstOrNull { it.id == findingId }
}
