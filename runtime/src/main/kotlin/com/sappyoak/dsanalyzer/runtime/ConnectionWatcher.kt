package com.sappyoak.dsanalyzer.runtime

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.Processes

public sealed interface ConnectionEvent {
    /** No game is connected. Emitted first and again whenever a game exists or a refused on goes away */
    public data object Searching : ConnectionEvent

    public data class Connected(public val connection: GameConnection) : ConnectionEvent

    /** [game] was found but could not be attached to. It is not retried until it exists */
    public data class Refused(public val game: GameProcess, public val reason: String) : ConnectionEvent
}

/**
 * Keeps a connection to whichever of [editions] is running, for as long as the flow is collected.
 *
 * Searches every [interval] until a game appears, holds the connection while it runs, and goes back
 * to searching when it exists. Cancelling the collection closes any open connection, so disconnecting
 * is just cancelling.
 *
 * Process calls block, so the watch runs on [context]
 */
public fun Processes.watchForGame(
    editions: Set<GameEdition>,
    interval: Duration = 1.seconds,
    context: CoroutineContext = Dispatchers.IO
): Flow<ConnectionEvent> = flow {
    val refused = mutableSetOf<Int>()
    emit(ConnectionEvent.Searching)

    while (true) {
        val games = findGames(editions)
        if (refused.retainAll(games.map { it.process.pid }.toSet())) {
            emit(ConnectionEvent.Searching)
        }

        val candidate = games.firstOrNull { it.process.pid !in refused }
        when (val result = candidate?.let { connect(it) }) {
            null -> Unit
            is ConnectResult.Refused -> {
                refused.add(candidate.process.pid)
                emit(ConnectionEvent.Refused(candidate, result.reason))
            }
            is ConnectResult.Connected -> holdWhileRunning(result.connection, interval)
        }

        delay(interval)
    }
}.flowOn(context)

private suspend fun FlowCollector<ConnectionEvent>.holdWhileRunning(
    connection: GameConnection,
    interval: Duration
) {
    connection.use {
        emit(ConnectionEvent.Connected(it))
        while (it.isRunning) {
            delay(interval)
        }
    }
    emit(ConnectionEvent.Searching)
}