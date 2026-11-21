package com.sappyoak.dsanalyzer.runtime.sampling

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transformLatest
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.ConnectionEvent
import com.sappyoak.dsanalyzer.runtime.EditionTables
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.read.readBuild
import com.sappyoak.dsanalyzer.runtime.session.FocusRequest
import com.sappyoak.dsanalyzer.runtime.session.RuntimeEvent
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import com.sappyoak.dsanalyzer.runtime.session.animation.AnimationTarget
import com.sappyoak.dsanalyzer.runtime.tablesFor
import kotlin.time.TimeSource


private val logger = KotlinLogging.logger {  }

/**
 * Work a caller wants done inside the sampling loop
 */
public class SampleRequests {
    private val placedEnemies = AtomicBoolean(false)
    private val characterEffects = AtomicBoolean(false)
    private val ai = AtomicReference(AIScope.TargetingPlayer)
    private val animated = AtomicReference<AnimationTarget>(AnimationTarget.Player)
    private val followed = AtomicReference(FocusRequest.Off)
    private val watched = AtomicReference<Int?>(null)

    public val writes: WriteRequests = WriteRequests()

    internal val aiScope: AIScope get() = ai.get()
    internal val animationTarget: AnimationTarget get() = animated.get()
    internal val focus: FocusRequest get() = followed.get()
    internal val watchedFlag: Int? get() = watched.get()

    public fun requestPlacedEnemies() {
        placedEnemies.set(true)
    }

    internal fun takePlacedEnemies(): Boolean = placedEnemies.getAndSet(false)

    public fun requestCharacterEffects() {
        characterEffects.set(true)
    }

    internal fun takeCharacterEffects(): Boolean = characterEffects.getAndSet(false)

    public fun setAIScope(scope: AIScope) {
        ai.set(scope)
    }

    public fun setAnimationTarget(target: AnimationTarget) {
        animated.set(target)
    }

    public fun setFocus(request: FocusRequest) {
        followed.set(request)
    }

    public fun watchFlag(flagId: Int?) {
        watched.set(flagId)
    }
}

/**
 * Samples each connection for as long as it lasts.
 *
 * The connection is never handed downstream and stays inside this flow, so a game going
 * away cancels the sampling that was reading it rather than something holding a dead handle
 */
public fun Flow<ConnectionEvent>.sampling(
    requests: SampleRequests,
    extensions: () -> List<SessionExtension> = ::emptyList,
    period: (GameEdition) -> Duration = ::tickPeriod,
    tables: (GameEdition) -> EditionTables? = ::tablesFor,
    context: CoroutineContext = Dispatchers.IO,
    clock: TimeSource = TimeSource.Monotonic
): Flow<RuntimeEvent> = transformLatest { event ->
    when (event) {
        ConnectionEvent.Searching -> emit(RuntimeEvent.Searching)
        is ConnectionEvent.Refused -> emit(RuntimeEvent.Refused(event.game, event.reason))
        is ConnectionEvent.Connected -> sampleWhileConnected(event.connection, requests, extensions, period, tables, clock)
    }
}.flowOn(context)

private fun tickPeriod(edition: GameEdition): Duration = 1.seconds / edition.logicTickHz

private suspend fun FlowCollector<RuntimeEvent>.sampleWhileConnected(
    connection: GameConnection,
    requests: SampleRequests,
    extensions: () -> List<SessionExtension>,
    period: (GameEdition) -> Duration,
    tables: (GameEdition) -> EditionTables?,
    clock: TimeSource
) {
    val game = connection.game
    val edition = tables(game.edition)
    if (edition == null) {
        emit(RuntimeEvent.Unsupported(game))
        return
    }

    val session = connection.openSession(edition, extensions())
    val build = readBuild(game.edition, connection.memory, connection.mainModule)

    emit(RuntimeEvent.Attached(game, build, session.pointers, session.supports))

    val pacer = TickPacer(period(game.edition), clock)
    val retry = SignatureRetry()

    try {
        sampleUntilCancelled(connection, session, edition, requests, pacer, retry)
    } finally {
        if (connection.isRunning) {
            val reverted = session.revertAll()
            logger.atInfo {
                message = "Reverted the tool's writes on detach"
                payload = mapOf("reverted" to reverted)
            }
        }
    }
}

private suspend fun FlowCollector<RuntimeEvent>.sampleUntilCancelled(
    connection: GameConnection,
    session: GameSession,
    edition: EditionTables,
    requests: SampleRequests,
    pacer: TickPacer,
    retry: SignatureRetry
) {
    val watch = SampleWatch(pacer.period)

    while (true) {
        val snapshot = session.sample(
            aiScope = requests.aiScope,
            animation = requests.animationTarget,
            focusRequest = requests.focus,
            watchedFlag = requests.watchedFlag
        )
        watch.record(snapshot.cost.elapsed)
        emit(RuntimeEvent.Sampled(snapshot))
        requests.writes.applyTo(session)
        session.afterSample()

        if (!session.pointers.complete && retry.due()) {
            session.resolveMissing(edition.pointers, connection.mainModule)?.let { emit(RuntimeEvent.Resolved(it)) }
        }

        if (requests.takePlacedEnemies()) {
            logger.debug { "Reading the enemies the loaded blocks place" }
            emit(RuntimeEvent.PlacedEnemies(session.readPlacedEnemies()))
        }

        if (requests.takeCharacterEffects()) {
            logger.debug { "Reading every character's special effects" }
            emit(RuntimeEvent.CharacterEffects(session.readCharacterEffects()))
        }

        pacer.awaitNext()
    }
}
