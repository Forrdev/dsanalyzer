package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.tas.rng.RngState

/** Pad input for consecutive frames, the first played on the first frame after it starts */
public data class Movie(public val frames: List<PadFrame>) {
    public val size: Int get() = frames.size

    /** Frames [from] until [until] */
    public fun slice(from: Int, until: Int = size): Movie = Movie(frames.subList(from, until).toList())

    public operator fun plus(other: Movie): Movie = Movie(frames + other.frames)

    /** The rerecord: everything before frame [at] kept, everything from it replaced by [replacement] */
    public fun splice(at: Int, replacement: Movie): Movie {
        require(at in 0..size) { "Frame $at is outside a movie of $size" }
        return Movie(frames.subList(0, at) + replacement.frames)
    }

    /** Drops the frames before the first one with any input */
    public fun trimLeadingIdle(deadzone: Float = PadFrame.DEFAULT_DEADZONE): Movie =
        Movie(frames.dropWhile { it.isIdle(deadzone) })

    public companion object {
        public val Empty: Movie = Movie(emptyList())
    }
}

/** A point playback waits for before a segment starts, because the frames to it vary from run to run */
public enum class SyncPoint(internal val code: Int) {
    /** The first frame of play after a load screen */
    LoadFinished(1);

    internal companion object {
        fun of(code: Int): SyncPoint? = entries.firstOrNull { it.code == code }
    }
}

/**
 * A movie that starts once [waitFor] has passed, or straight after the segment before it. [rng] is
 * pinned while the gate holds the frame the segment starts on, so only the first segment of a run
 * (started while paused) or one with a sync point can carry it
 */
public data class Segment(
    public val movie: Movie,
    public val waitFor: SyncPoint? = null,
    public val rng: RngState? = null
)

/**
 * A whole TAS: segments authored apart and played as one. Consecutive segments with no sync point
 * between them play as a single movie, so no frame is lost at the seam
 */
public data class Run(public val segments: List<Segment>) {
    /** The run as playback sees it: one movie per sync point, the first with whatever precedes any */
    internal fun chunks(): List<Segment> = segments.fold(mutableListOf<Segment>()) { chunks, segment ->
        val last = chunks.lastOrNull()
        if (last != null && segment.waitFor == null) chunks[chunks.lastIndex] = last.copy(movie = last.movie + segment.movie)
        else chunks += segment
        chunks
    }

    /** Whether every segment's RNG can be pinned: the first's, or one at a sync point */
    public val pinsAreHeld: Boolean
        get() = segments.drop(1).all { it.rng == null || it.waitFor != null }

    public companion object {
        public fun of(movie: Movie): Run = Run(listOf(Segment(movie)))
    }
}
