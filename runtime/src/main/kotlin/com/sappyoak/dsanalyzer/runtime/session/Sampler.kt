package com.sappyoak.dsanalyzer.runtime.session

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

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.ConnectionEvent
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.tablesFor


private val logger = KotlinLogging.logger {  }

/**
 * Work a caller wants done inside the sampling loop
 */
public class SampleRequests {
    private val placedEnemies = AtomicBoolean(false)
    private val collision = AtomicBoolean(false)
    private val characterEffects = AtomicBoolean(false)

    public fun requestPlacedEnemies() {
        placedEnemies.set(true)
    }

    internal fun takePlacedEnemies(): Boolean = placedEnemies.getAndSet(false)

    public fun requestCollision() {
        collision.set(true)
    }

    internal fun takeCollision(): Boolean = collision.getAndSet(false)

    public fun requestCharacterEffects() {
        characterEffects.set(true)
    }

    internal fun takeCharacterEffects(): Boolean = characterEffects.getAndSet(false)
}

/**
 * Samples each connection for as long as it lasts.
 *
 * The connection is never handed downstream and stays inside this flow, so a game going
 * away cancels the sampling that was reading it rather than something holding a dead handle
 */
public fun Flow<ConnectionEvent>.sampling(
    requests: SampleRequests,
    period: (GameEdition) -> Duration = ::tickPeriod,
    tables: (GameEdition) -> EditionTables? = ::tablesFor,
    context: CoroutineContext = Dispatchers.IO
): Flow<RuntimeEvent> = transformLatest { event ->
    when (event) {
        ConnectionEvent.Searching -> emit(RuntimeEvent.Searching)
        is ConnectionEvent.Refused -> emit(RuntimeEvent.Refused(event.game, event.reason))
        is ConnectionEvent.Connected -> sampleWhileConnected(event.connection, requests, period, tables)
    }
}.flowOn(context)

private fun tickPeriod(edition: GameEdition): Duration = 1.seconds / edition.logicTickHz

private suspend fun FlowCollector<RuntimeEvent>.sampleWhileConnected(
    connection: GameConnection,
    requests: SampleRequests,
    period: (GameEdition) -> Duration,
    tables: (GameEdition) -> EditionTables?
) {
    val game = connection.game
    val edition = tables(game.edition)
    if (edition == null) {
        emit(RuntimeEvent.Unsupported(game))
        return
    }

    val session = connection.openSession(edition)
    val build = edition.buildOf(connection.memory, connection.mainModule)


    emit(RuntimeEvent.Attached(game, build, session.pointers))

    val wait = period(game.edition)
    val retry = SignatureRetry()

    while (true) {
        emit(RuntimeEvent.Sampled(session.sample()))
        if (!session.pointers.complete && retry.due()) {
            session.resolveMissing(edition.pointers, connection.mainModule)?.let { emit(RuntimeEvent.Resolved(it)) }
        }

        // After the tick rather than before it so an expensive read never delays the sample it shares
        // an instant with
        if (requests.takePlacedEnemies()) {
            logger.debug { "Reading the enemies the loaded blocks place" }
            emit(RuntimeEvent.PlacedEnemies(session.readPlacedEnemies()))
        }
        if (requests.takeCollision()) {
            logger.debug { "Reading the fixed island's collision geometry" }
            emit(RuntimeEvent.Collision(session.readCollision()))
        }
        if (requests.takeCharacterEffects()) {
            logger.debug { "Reading every character's special effects" }
            emit(RuntimeEvent.CharacterEffects(session.readCharacterEffects()))
        }

         delay(wait)
    }
}
