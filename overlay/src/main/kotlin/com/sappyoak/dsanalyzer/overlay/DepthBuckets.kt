package com.sappyoak.dsanalyzer.overlay

/** How many depth slices the draw radius is cut into */
internal const val DEPTH_BUCKETS: Int = 64

internal class DepthBuckets(capacity: Int) {
    /** Triangle indices, farthest bucket first. Valid up to [count] */
    val order: IntArray = IntArray(capacity)

    var count: Int = 0
        private set

    private val bucketOf = IntArray(capacity)
    private val start = IntArray(DEPTH_BUCKETS)
    private val size = IntArray(DEPTH_BUCKETS)

    /**
     * Where the next triangle of each slice goes while placing them.
     */
    private val cursor = IntArray(DEPTH_BUCKETS)

    /**
     * Groups the first [triangles] entries of [depths] into slices spanning zero to [far].
     */
    fun build(depths: FloatArray, triangles: Int, far: Float) {
        count = triangles
        size.fill(0)
        if (triangles <= 0) return

        val span = if (far > 0f) far else 1f
        val scale = (DEPTH_BUCKETS - 1) / span

        for (i in 0 until triangles) {
            val bucket = (depths[i] * scale).toInt().coerceIn(0, DEPTH_BUCKETS - 1)
            bucketOf[i] = bucket
            size[bucket]++
        }

        // Descending, so the farthest slice takes the first run of `order` and iterating the array
        // forwards is already back-to-front
        var at = 0
        for (bucket in DEPTH_BUCKETS - 1 downTo 0) {
            start[bucket] = at
            at += size[bucket]
        }

        start.copyInto(cursor)
        for (i in 0 until triangles) {
            val bucket = bucketOf[i]
            order[cursor[bucket]] = i
            cursor[bucket]++
        }
    }

    /** Where slice [bucket] begins in [order] */
    fun startOf(bucket: Int): Int = start[bucket]

    /** How many triangles slice [bucket] holds, which is commonly zero */
    fun sizeOf(bucket: Int): Int = size[bucket]

    fun nearness(bucket: Int): Float = 1f - bucket.toFloat() / (DEPTH_BUCKETS - 1)
}