package com.sappyoak.dsanalyzer.runtime.session.effects

/** Ten buckets of three bits, so thirty of the word's thirty-two bits are used */
public const val STATE_INFO_BUCKETS: Int = 10

/** A bucket holds a count of 0–7, where seven means "seven or more" */
public const val STATE_INFO_SATURATED: Int = 7

private const val BUCKET_BITS = 3

/** Nine distinct `stateInfo` values share each bucket, so one wrong bucket hides a whole family */
private const val BUCKET_DIVISOR = 9

public fun bucketOf(stateInfo: Int): Int =
    if (stateInfo == 0) 0 else (stateInfo % BUCKET_DIVISOR) + 1

@JvmInline
public value class StateInfoCounters(public val word: Int) {
    public fun count(bucket: Int): Int =
        (word ushr (bucket * BUCKET_BITS)) and STATE_INFO_SATURATED

    public fun saturated(bucket: Int): Boolean = count(bucket) == STATE_INFO_SATURATED

    public val counts: List<Int> get() = List(STATE_INFO_BUCKETS) { count(it) }
    public val total: Int get() = counts.sum()
}

public data class BucketDisagreement(
    public val bucket: Int,
    public val counted: Int,
    public val walked: Int
) {
    public val hidesEffects: Boolean get() = counted < walked
}