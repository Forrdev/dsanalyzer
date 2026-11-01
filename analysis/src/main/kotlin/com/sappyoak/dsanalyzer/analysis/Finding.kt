package com.sappyoak.dsanalyzer.analysis

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlin.time.Instant

import com.sappyoak.dsanalyzer.game.GameEdition

@JvmInline
@Serializable
public value class FindingId(public val value: String) {
    override fun toString(): String = value
}

/**
 * One claim, what backs it, and what is still unknown about it
 */
@Serializable
public data class Finding(
    public val id: FindingId,
    public val claim: String,
    public val evidence: List<Evidence> = emptyList(),
    public val missing: String? = null,
    public val supersedes: FindingId? = null,
    @Contextual public val created: Instant,
    @Contextual public val updated: Instant
) {
    init {
        require(claim.isNotBlank()) { "A finding is its claim; '$id' has none" }
        require(supersedes != id) { "A finding cannot supersede itself: '$id'" }
        require(updated >= created) { "'$id' was updated before it was created" }
    }
}

public val Finding.confirmedOn: Set<GameEdition>
    get() = evidence
        .filterIsInstance<Evidence.MeasuredLive>()
        .mapTo(mutableSetOf()) { it.observedOn.edition }

public val Finding.isOnlyInferred: Boolean
    get() = evidence.isNotEmpty() && evidence.all { it is Evidence.Inferred }
