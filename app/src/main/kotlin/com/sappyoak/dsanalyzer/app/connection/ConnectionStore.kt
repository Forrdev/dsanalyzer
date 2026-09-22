package com.sappyoak.dsanalyzer.app.connection

import com.sappyoak.dsanalyzer.app.store.Store
import kotlinx.coroutines.CoroutineScope

public class ConnectionStore(
    scope: CoroutineScope,
    effects: ConnectionEffects
) : Store<ConnectionState, ConnectionMessage, ConnectionEffect>(
    scope = scope,
    name = "connection",
    initial = ConnectionState(),
    reduce = ::reduceConnection,
    effects = effects
)