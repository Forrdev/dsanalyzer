package com.sappyoak.dsanalyzer.tas

import java.util.concurrent.ConcurrentLinkedQueue

/** Something asked of the TAS tools, carried out on the sampling thread after the next sample */
public sealed interface TasRequest {
    public data object Install : TasRequest
    public data object Remove : TasRequest
    public data object Pause : TasRequest
    public data object Resume : TasRequest

    public data class Step(public val frames: Int) : TasRequest {
        init {
            require(frames > 0) { "Step at least one frame" }
        }
    }

    public data class PauseAt(public val point: SyncPoint) : TasRequest
    public data class Play(public val run: Run) : TasRequest
    public data object Record : TasRequest

    /** Ends a run or a recording; a recording's movie shows up in [TasState.On.recorded] */
    public data object Stop : TasRequest

    /** Keeps the player's map, position and facing as [TasState.start] */
    public data object CaptureStart : TasRequest

    /** Warps to [start], or to the last captured one when null */
    public data class RestoreStart(public val start: StartState? = null) : TasRequest
}

/** Requests queued from any thread, taken in order by the sampling thread */
internal class TasRequests {
    private val queue = ConcurrentLinkedQueue<TasRequest>()

    fun add(request: TasRequest) {
        queue.add(request)
    }

    fun next(): TasRequest? = queue.poll()
}
