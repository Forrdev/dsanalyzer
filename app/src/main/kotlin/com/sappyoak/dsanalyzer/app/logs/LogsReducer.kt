package com.sappyoak.dsanalyzer.app.logs

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceLogs(state: LogsState, message: LogsMessage): Transition<LogsState, LogsEffect> = when (message) {
    LogsMessage.Start -> state.with(LogsEffect.Follow)
    is LogsMessage.Read -> state.read(message.batch).with()
    is LogsMessage.Failed -> state.copy(failure = message.reason).with()
    is LogsMessage.FilterChanged -> state.copy(filter = message.filter).with()
    is LogsMessage.EntrySelected -> state.copy(selected = message.sequence).with()
    is LogsMessage.ProblemSelected -> state.copy(selectedProblem = message.key).with()
}

private fun LogsState.read(batch: LogBatch): LogsState {
    val base = if (batch.restarted) LogsState(filter = filter) else this
    return base.copy(
        entries = (base.entries + batch.entries).takeLast(KEPT_ENTRIES),
        failure = batch.problem
    )
}
