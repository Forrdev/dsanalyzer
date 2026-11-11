package com.sappyoak.dsanalyzer.tas

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import com.sappyoak.dsanalyzer.runtime.sampling.SessionContext
import com.sappyoak.dsanalyzer.runtime.sampling.SessionExtension

/** Where the TAS tools stand, published after every sample */
public sealed interface TasState {
    /** The last captured start, kept whether or not the hooks are in */
    public val start: StartState?

    public data class Off(override val start: StartState? = null) : TasState
    public data class Unavailable(public val reason: String, override val start: StartState?) : TasState

    /** [run] is null unless a run is playing; [recorded] is the last movie a stopped recording made */
    public data class On(
        public val frame: Int?,
        public val paused: Boolean,
        public val triggered: Boolean,
        public val pad: PadProgress,
        public val run: RunProgress?,
        public val recorded: Movie?,
        override val start: StartState?
    ) : TasState
}

/**
 * The TAS tools as a session extension: requests are queued from anywhere and carried out on the
 * sampling thread, and the state is published as its own flow rather than in the runtime snapshot
 *
 * One instance outlives sessions; each [attached] starts it over against the new game
 */
public class TasControl : SessionExtension {
    private val requests = TasRequests()
    private val mutableState = MutableStateFlow<TasState>(TasState.Off())

    private var context: SessionContext? = null
    private var starts: StartStates? = null
    private var tas: Tas? = null
    private var unavailable: String? = null
    private var recorded: Movie? = null
    private var start: StartState? = null

    public val state: StateFlow<TasState> = mutableState.asStateFlow()

    public fun request(request: TasRequest) {
        requests.add(request)
    }

    override fun attached(context: SessionContext) {
        this.context = context
        starts = StartStates(context.memory, context.writer)
        tas = null
        unavailable = null
        publish()
    }

    override fun afterSample() {
        while (true) apply(requests.next() ?: break)
        tas?.pump()
        publish()
    }

    /** The hooks come out first, so a held game is released and the pad handed back */
    override fun release() {
        tas?.close()
        tas = null
        publish()
    }

    private fun apply(request: TasRequest) {
        val tas = tas
        when (request) {
            TasRequest.Install -> install()
            TasRequest.Remove -> release()
            TasRequest.Pause -> tas?.gate?.pause()
            TasRequest.Resume -> tas?.gate?.resume()
            is TasRequest.Step -> tas?.gate?.step(request.frames)
            is TasRequest.PauseAt -> tas?.pauseAt(request.point)
            is TasRequest.Play -> tas?.runs?.play(request.run)
            TasRequest.Record -> tas?.let {
                it.runs.stop()
                it.pad.record()
            }
            TasRequest.Stop -> tas?.let {
                it.runs.stop()
                it.pad.stop()?.let { movie -> recorded = movie }
            }
            TasRequest.CaptureStart -> captureStart()
            is TasRequest.RestoreStart -> restoreStart(request.start ?: start)
        }
    }

    private fun install() {
        if (tas != null) return
        val context = context ?: return
        when (val result = Tas.install(context.edition, context.memory.memory, context.module, context.writer)) {
            is Tas.Result.Installed -> {
                tas = result.tas
                unavailable = null
            }
            is Tas.Result.Unavailable -> unavailable = result.reason
        }
    }

    private fun captureStart() {
        val here = context?.place ?: return
        starts?.capture(here)?.let { start = it }
    }

    private fun restoreStart(target: StartState?) {
        val here = context?.place ?: return
        if (target != null) starts?.restore(target, here)
    }

    private fun publish() {
        mutableState.value = tas?.let {
            val gate = it.gate
            TasState.On(gate.frame, gate.isPaused, gate.isTriggered, it.pad.progress, it.runs.progress, recorded, start)
        } ?: unavailable?.let { TasState.Unavailable(it, start) } ?: TasState.Off(start)
    }
}
