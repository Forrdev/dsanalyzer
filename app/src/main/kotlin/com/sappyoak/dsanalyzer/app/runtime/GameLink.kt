package com.sappyoak.dsanalyzer.app.runtime

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.RuntimeEvent
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.session.SampleRequests
import com.sappyoak.dsanalyzer.runtime.session.sampling
import com.sappyoak.dsanalyzer.runtime.watchForGame

private val logger = KotlinLogging.logger {  }

/** Room for the occasional status change, since ticks do not travel this way */
private const val EVENT_BUFFER = 16

/** What is attached and what it last reported */
public data class LinkState(
    public val attached: AttachedGame? = null,
    public val note: String? = null,
    public val snapshot: RuntimeSnapshot? = null,
    /**
     * The last answer to a placed enemy request, which is not part of a tick.
     * Null until something asks. It is deliberately not cleared as the world changes.
     * A roster from the block you just left is stale rather than wrong, and dropping it would
     * empty the view everytime the player moved
     */
    public val placedEnemies: List<BlockRoster>? = null
)

public class GameLink(
    private val scope: CoroutineScope,
    private val processes: () -> Processes,
    private val editions: Set<GameEdition> = GameEdition.entries.toSet()
) {
    private val mutableState = MutableStateFlow(LinkState())
    private val mutableEvents = MutableSharedFlow<RuntimeEvent>(
        extraBufferCapacity = EVENT_BUFFER,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    public val state: StateFlow<LinkState> = mutableState.asStateFlow()
    public val events: SharedFlow<RuntimeEvent> = mutableEvents.asSharedFlow()

    private var watch: Job? = null
    private val requests = SampleRequests()

    /** Begins watching */
    public fun start() {
        if (watch?.isActive == true) return

        watch = scope.launch {
            try {
                processes().watchForGame(editions).sampling().collect(::record)
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (err: Throwable) {
                logger.atError {
                    message = "The watch for the game stopped"
                    cause = err
                }
                record(RuntimeEvent.Failed(err.toString()))
            }
        }
    }

    /** Stops watching and closes whatever was attached */
    public fun stop() {
        watch?.cancel()
        watch = null
        mutableState.value = LinkState()
    }

    /**
     * Asks the sampling loop for every enemy the loaded blocks place
     */
    public fun requestPlacedEnemies() {
        requests.requestPlacedEnemies()
    }

    private suspend fun record(event: RuntimeEvent) {
        when (event) {
            is RuntimeEvent.Sampled -> mutableState.update { it.copy(snapshot = event.snapshot) }
            is RuntimeEvent.PlacedEnemies -> mutableState.update { it.copy(placedEnemies = event.rosters) }
            is RuntimeEvent.Attached -> {
                val attached = event.describe()
                logger.atInfo {
                    message = "Attached to the game"
                    payload = mapOf(
                        "pid" to attached.pid,
                        "executable" to attached.executableName,
                        "build" to attached.build?.name.orEmpty(),
                        "unresolved" to attached.unresolved.size
                    )
                }
                publish(LinkState(attached = attached), event)
            }
            RuntimeEvent.Searching -> publish(LinkState(), event)
            is RuntimeEvent.Refused -> publish(LinkState(note =  event.reason), event)
            is RuntimeEvent.Unsupported -> publish(LinkState(note = "${event.game.edition.name} is not read yet"), event)
            is RuntimeEvent.Failed -> publish(LinkState(note = event.reason), event)
        }
    }

    private suspend fun publish(state: LinkState, event: RuntimeEvent) {
        mutableState.value = state
        mutableEvents.emit(event)
    }
}

private fun RuntimeEvent.Attached.describe(): AttachedGame = AttachedGame(
    executableName = game.process.executableName,
    pid = game.process.pid,
    edition = game.edition,
    build = build,
    unresolved = pointers.unresolved.map { it.signature }
)