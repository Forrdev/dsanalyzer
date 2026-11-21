package com.sappyoak.dsanalyzer.runtime.sampling

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transformLatest
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.ConnectionEvent
import com.sappyoak.dsanalyzer.runtime.edition.Edition
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.edition.editionFor
import com.sappyoak.dsanalyzer.runtime.snapshot.RuntimeEvent
import com.sappyoak.dsanalyzer.runtime.write.SessionExtension

private val logger = KotlinLogging.logger {  }


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
    editions: (GameEdition) -> Edition? = ::editionFor,
    context: CoroutineContext = Dispatchers.IO,
    clock: TimeSource = TimeSource.Monotonic
): Flow<RuntimeEvent> = transformLatest { event ->
    when (event) {
        ConnectionEvent.Searching -> emit(RuntimeEvent.Searching)
        is ConnectionEvent.Refused -> emit(RuntimeEvent.Refused(event.game, event.reason))
        is ConnectionEvent.Connected -> sampleWhileConnected(event.connection, requests, extensions, period, editions, clock)
    }
}.flowOn(context)

private suspend fun FlowCollector<RuntimeEvent>.sampleWhileConnected(
    connection: GameConnection,
    requests: SampleRequests,
    extensions: () -> List<SessionExtension>,
    period: (GameEdition) -> Duration,
    editions: (GameEdition) -> Edition?,
    clock: TimeSource
) {
    val game = connection.game
    val edition = editions(game.edition)
    if (edition == null) {
        emit(RuntimeEvent.Unsupported(game))
        return
    }

    val session = connection.openSession(edition, extensions())
    val build = edition.build(connection.memory, connection.mainModule)

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
    edition: Edition,
    requests: SampleRequests,
    pacer: TickPacer,
    retry: SignatureRetry
) {
    val watch = SampleWatch(pacer.period)

    while (true) {
        val snapshot = session.sample(requests.options)
        watch.record(snapshot.cost.elapsed)
        emit(RuntimeEvent.Sampled(snapshot))
        requests.writes.drain(session::apply)
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

private fun tickPeriod(edition: GameEdition): Duration = 1.seconds / edition.logicTickHz

