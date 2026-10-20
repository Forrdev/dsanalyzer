package com.sappyoak.dsanalyzer.runtime.session.effects

public data class ActiveEffect(
    public val effectId: Int,
    public val stateInfo: Int?,
    public val flags: Int,
    public val excluded: Boolean,
    public val live: Boolean,
    public val durationRemaining: Float?,
    public val durationTotal: Float?,
    public val magnitude: Float?,
    public val duration: Float?,
    public val maxHpRate: Float?,
    public val unlinkId: Int?
) {
    public val bucket: Int? get() = stateInfo?.let(::bucketOf)
    public val liveOnlyMismatch: Boolean get() = !excluded && !live
}

public data class SpecialEffectSnapshot(
    public val effects: List<ActiveEffect>,
    public val counters: StateInfoCounters,
    public val movedRows: Set<Int>,
    public val truncated: Boolean
) {
    public val walkedCounts: List<Int>
        get() {
            val counts = IntArray(STATE_INFO_BUCKETS)
            for (effect in effects) {
                val bucket = effect.bucket ?: 0
                if (bucket in counts.indices) counts[bucket]++
            }
            return counts.map { it.coerceAtMost(STATE_INFO_SATURATED) }
        }

    public val disagreements: List<BucketDisagreement>
        get() = walkedCounts.withIndex().mapNotNull { (bucket, walked) ->
            val counted = counters.count(bucket)
            if (counted == walked) null else BucketDisagreement(bucket, counted, walked)
        }

    public val desynced: Boolean get() = disagreements.isNotEmpty()

    public val invisibleButCounted: List<ActiveEffect> get() = effects.filter { it.excluded }
    public val liveOnlyMismatches: List<ActiveEffect> get() = effects.filter { it.liveOnlyMismatch }
    public val nodesWithoutRows: Int get() = effects.count { it.stateInfo == null }
}