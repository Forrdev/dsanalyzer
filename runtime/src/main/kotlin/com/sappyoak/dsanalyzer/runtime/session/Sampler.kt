package com.sappyoak.dsanalyzer.runtime.session

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transformLatest
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.runtime.ConnectionEvent
import com.sappyoak.dsanalyzer.runtime.GameConnection
import com.sappyoak.dsanalyzer.runtime.pointers.GamePointer
import com.sappyoak.dsanalyzer.runtime.ptde.PTDEPointers
import com.sappyoak.dsanalyzer.runtime.ptde.ptdeBuild

/**
 * Samples each connection for as long as it lasts.
 *
 * The connection is never handed downstream and stays inside this flow, so a game going
 * away cancels the sampling that was reading it rather than something holding a dead handle
 */
public fun Flow<ConnectionEvent>.sampling(
    period: (GameEdition) -> Duration = ::tickPeriod,
    tables: (GameEdition) -> List<GamePointer>? = ::tablesFor,
    context: CoroutineContext = Dispatchers.IO
): Flow<RuntimeEvent> = transformLatest { event ->
    when (event) {
        ConnectionEvent.Searching -> emit(RuntimeEvent.Searching)
        is ConnectionEvent.Refused -> emit(RuntimeEvent.Refused(event.game, event.reason))
        is ConnectionEvent.Connected -> sampleWhileConnected(event.connection, period, tables)
    }
}.flowOn(context)

private fun tickPeriod(edition: GameEdition): Duration = 1.seconds / edition.logicTickHz
private fun tablesFor(edition: GameEdition): List<GamePointer>? = when (edition) {
    GameEdition.PrepareToDie -> PTDEPointers
    else -> null
}

private suspend fun FlowCollector<RuntimeEvent>.sampleWhileConnected(
    connection: GameConnection,
    period: (GameEdition) -> Duration,
    tables: (GameEdition) -> List<GamePointer>?
) {
    val game = connection.game
    val pointers = tables(game.edition)
    if (pointers == null) {
        emit(RuntimeEvent.Unsupported(game))
        return
    }

    val session = connection.openSession(pointers)
    val build = if (game.edition == GameEdition.PrepareToDie) {
        connection.memory.ptdeBuild(connection.mainModule)
    } else {
        null
    }


    emit(RuntimeEvent.Attached(game, build, session.pointers))

    val wait = period(game.edition)
    while (true) {
        emit(RuntimeEvent.Sampled(session.sample()))
        delay(wait)
    }
}