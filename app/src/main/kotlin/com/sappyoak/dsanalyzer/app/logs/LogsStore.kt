package com.sappyoak.dsanalyzer.app.logs

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class LogsStore(
    scope: CoroutineScope,
    effects: LogsEffects
) : Store<LogsState, LogsMessage, LogsEffect>(
    scope = scope,
    name = "logs",
    initial = LogsState(),
    reduce = ::reduceLogs,
    effects = effects
)
