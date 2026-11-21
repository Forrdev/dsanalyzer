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
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.sappyoak.dsanalyzer.app.logging.LogContext
import com.sappyoak.dsanalyzer.app.logging.LogContextKey
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.runtime.pointers.ResolvedPointers
import com.sappyoak.dsanalyzer.runtime.session.BlockRoster
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.RuntimeEvent
import com.sappyoak.dsanalyzer.runtime.session.RuntimeSnapshot
import com.sappyoak.dsanalyzer.runtime.sampling.SampleRequests
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget
import com.sappyoak.dsanalyzer.runtime.sampling.sampling
import com.sappyoak.dsanalyzer.runtime.session.EngineDrawRequest
import com.sappyoak.dsanalyzer.runtime.session.EngineDrawStatus
import com.sappyoak.dsanalyzer.runtime.watchForGame
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.EngineDrawExtension
import com.sappyoak.dsanalyzer.runtime.write.debugdraw.EngineDrawRequests

private val logger = KotlinLogging.logger {  }

/** Room for the occasional status change, since ticks do not travel this way */
private const val EVENT_BUFFER = 16

/** What is attached and what it last reported */
public data class LinkState(
    public val attached: AttachedGame? = null,
    public val note: String? = null,
    public val snapshot: RuntimeSnapshot? = null,
    public val placedEnemies: List<BlockRoster>? = null,
    public val engineDraw: EngineDrawStatus? = null,
    public val characterEffects: List<CharacterSnapshot>? = null
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
    private val draws = EngineDrawRequests()
    private val maps = MapFollower()

    /** Begins watching */
    public fun start() {
        if (watch?.isActive == true) return

        watch = scope.launch {
            try {
                processes().watchForGame(editions)
                    .sampling(requests, extensions = { listOf(EngineDrawExtension(draws, ::drawChanged)) })
                    .collect(::record)
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

    public fun requestCharacterEffects() {
        requests.requestCharacterEffects()
    }

    public fun setAIScope(scope: AIScope) {
        requests.setAIScope(scope)
    }

    public fun watchFlag(flagId: Int?) {
        requests.watchFlag(flagId)
    }

    public fun setAnimationTarget(target: AnimationTarget) {
        requests.setAnimationTarget(target)
    }

    public fun installEngineDraw(enabled: Boolean) {
        draws.install(enabled)
    }

    public fun setEngineDraw(request: EngineDrawRequest) {
        draws.set(request)
    }

    private suspend fun record(event: RuntimeEvent) {
        if (event is RuntimeEvent.Attached) event.log()
        mutableState.update { it.after(event) }
        if (event is RuntimeEvent.Sampled && maps.entered(event.snapshot)) readWorldAgain()
        if (event is RuntimeEvent.Attached || event == RuntimeEvent.Searching) maps.forget()
        if (event.changesConnection) mutableEvents.emit(event)
    }

    private fun readWorldAgain() {
        val before = mutableState.getAndUpdate { it.copy(placedEnemies = null) }
        if (before.placedEnemies != null) requests.requestPlacedEnemies()
    }

    private fun drawChanged(status: EngineDrawStatus) {
        mutableState.update { it.copy(engineDraw = status) }
    }

    private fun RuntimeEvent.Attached.log() {
        val attached = describe()
        logger.atInfo {
            message = "Attached to the game"
            payload = mapOf(
                "pid" to attached.pid,
                "executable" to attached.executableName,
                "build" to attached.build.orEmpty(),
                "supports" to attached.supports,
                "unresolved" to attached.unresolved.size
            )
        }
    }
}

internal fun LinkState.after(event: RuntimeEvent): LinkState = when (event) {
    is RuntimeEvent.Sampled -> copy(snapshot = event.snapshot)
    is RuntimeEvent.PlacedEnemies -> copy(placedEnemies = event.rosters)
    is RuntimeEvent.CharacterEffects -> copy(characterEffects = event.characters)
    is RuntimeEvent.Attached -> LinkState(attached = event.describe())
    is RuntimeEvent.Resolved -> copy(attached = attached?.copy(unresolved = event.pointers.unresolvedNames))
    RuntimeEvent.Searching -> LinkState()
    is RuntimeEvent.Refused -> LinkState(note = event.reason)
    is RuntimeEvent.Unsupported -> LinkState(note = "${event.game.edition.name} is not read yet")
    is RuntimeEvent.Failed -> LinkState(note = event.reason)
}

private val RuntimeEvent.changesConnection: Boolean
    get() = this !is RuntimeEvent.Sampled &&
            this !is RuntimeEvent.PlacedEnemies

private fun RuntimeEvent.Attached.describe(): AttachedGame = AttachedGame(
    executableName = game.process.executableName,
    pid = game.process.pid,
    edition = game.edition,
    build = build,
    unresolved = pointers.unresolvedNames,
    supports = supports
)

private val ResolvedPointers.unresolvedNames: List<String> get() = unresolved.map { it.signature }

private fun RuntimeEvent.logChange(before: LinkState, after: LinkState) {
    when (this) {
        is RuntimeEvent.Attached -> attached(after.attached ?: return)
        is RuntimeEvent.Resolved -> if (after.attached?.unresolved.orEmpty().isNotEmpty()) logger.atWarn {
            message = "Signatures still unresolved after a rescan"
            payload = mapOf("unresolved" to after.attached?.unresolved?.joinToString())
        }
        RuntimeEvent.Searching -> if (before.attached != null) {
            LogContext.set(LogContextKey.GameBuild, null)
            logger.atInfo { message = "The game closed; searching for it again" }
        }
        is RuntimeEvent.Refused -> logger.atWarn {
            message = "Refused to attach"
            payload = mapOf("executable" to game.process.executableName, "reason" to reason)
        }
        is RuntimeEvent.Unsupported -> logger.atInfo {
            message = "Found a game this tool does not read yet"
            payload = mapOf("edition" to game.edition.name, "executable" to game.process.executableName)
        }
        is RuntimeEvent.Failed, is RuntimeEvent.Sampled, is RuntimeEvent.PlacedEnemies,
        is RuntimeEvent.CharacterEffects -> Unit
    }
}

private fun attached(game: AttachedGame) {
    LogContext.set(LogContextKey.GameBuild, listOfNotNull(game.edition.name, game.build).joinToString(" "))
    logger.atInfo {
        message = "Attached to the game"
        payload = mapOf(
            "pid" to game.pid,
            "executable" to game.executableName,
            "build" to game.build.orEmpty(),
            "supports" to game.supports,
            "unresolved" to game.unresolved.size
        )
    }
    if (game.unresolved.isNotEmpty()) logger.atWarn {
        message = "Signatures not found on attach"
        payload = mapOf("unresolved" to game.unresolved.joinToString())
    }
}
