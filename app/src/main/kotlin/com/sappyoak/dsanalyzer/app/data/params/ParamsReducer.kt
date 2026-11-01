package com.sappyoak.dsanalyzer.app.data.params

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceParams(
    state: ParamsState,
    message: ParamsMessage
): Transition<ParamsState, ParamsEffect> = when (message) {
    is ParamsMessage.Opened -> ParamsState(installation = message.installation, loading = true)
        .with(ParamsEffect.Load(message.installation))

    is ParamsMessage.Loaded -> state.copy(params = message.params, names = message.names, loading = false).with()
    is ParamsMessage.QueryChanged -> state.copy(query = message.query).with()
    is ParamsMessage.Failed -> state.copy(loading = false, problem = message.reason).with()
}
