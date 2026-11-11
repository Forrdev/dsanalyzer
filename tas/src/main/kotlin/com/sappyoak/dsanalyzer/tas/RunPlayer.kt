package com.sappyoak.dsanalyzer.tas

import com.sappyoak.dsanalyzer.tas.rng.RngState

/** Where a [Run] stands: on [segment] of [segments], [waiting] for its sync point or playing [pad] */
public data class RunProgress(
    public val segment: Int,
    public val segments: Int,
    public val waiting: Boolean,
    public val pad: PadProgress
)

/**
 * Plays a [Run] chunk by chunk. At a sync point the pad is handed back and the gate armed to hold
 * the frame the point passes; the next chunk is queued while held and the hold released, so its
 * first frame is the first frame after the point. The player's own pad passes through while waiting.
 *
 * A chunk's RNG is pinned just before its movie is queued, while the gate holds the frame: the
 * run's own start (begin it paused) or the sync point it waited for
 */
public class RunPlayer internal constructor(
    private val gate: FrameGate,
    private val pad: PadOverride,
    private val syncPoints: Map<SyncPoint, GateTrigger>,
    private val pin: (RngState) -> Boolean
) {
    private var chunks: List<Segment> = emptyList()
    private var index = -1
    private var waiting = false

    @get:Synchronized
    public val progress: RunProgress?
        get() = if (index < 0) null else RunProgress(index, chunks.size, waiting, pad.progress)

    @Synchronized
    public fun play(run: Run): Boolean {
        val next = run.chunks()
        if (!run.pinsAreHeld || next.any { it.waitFor != null && it.waitFor !in syncPoints }) return false
        stop()
        chunks = next
        return begin(0)
    }

    @Synchronized
    public fun stop() {
        if (index < 0) return
        if (waiting) {
            gate.disarm()
            if (gate.isTriggered) gate.resume()
        } else {
            pad.stop()
        }
        index = -1
        waiting = false
    }

    /** Runs after the pad's own pump each sample */
    @Synchronized
    internal fun pump() {
        if (index < 0) return
        when {
            waiting && gate.isTriggered -> {
                waiting = false
                if (!start(chunks[index]) || !gate.resume()) stop()
            }
            !waiting && pad.progress.mode == PadMode.Off -> if (index + 1 < chunks.size) begin(index + 1) else stop()
        }
    }

    private fun begin(at: Int): Boolean {
        index = at
        val chunk = chunks[at]
        val point = chunk.waitFor ?: return start(chunk).also { if (!it) index = -1 }
        waiting = gate.pauseWhen(syncPoints.getValue(point))
        if (!waiting) index = -1
        return waiting
    }

    /** Pins the chunk's RNG, which needs the frame held, then queues its movie */
    private fun start(chunk: Segment): Boolean = (chunk.rng?.let(pin) ?: true) && pad.play(chunk.movie)
}
