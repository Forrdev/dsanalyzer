package com.sappyoak.dsanalyzer.app.connection

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceConnection(
    state: ConnectionState,
    message: ConnectionMessage
): Transition<ConnectionState, ConnectionEffect> = when (message) {
    ConnectionMessage.Start -> state.with(ConnectionEffect.CheckAutoConnect)

    ConnectionMessage.ConnectRequested -> state.connecting()

    ConnectionMessage.DisconnectRequested -> ConnectionState(ConnectionStatus.Off, state.target)
        .with(ConnectionEffect.StopWatching)

    is ConnectionMessage.TargetChanged -> when {
        message.installation == state.target -> state.with()
        !state.status.watching -> state.copy(target = message.installation).with()
        message.installation == null ->
            ConnectionState(ConnectionStatus.NoWorkspace).with(ConnectionEffect.StopWatching)
        else ->
            ConnectionState(ConnectionStatus.Searching, message.installation)
                .with(ConnectionEffect.Watch(message.installation))
    }

    is ConnectionMessage.AutoConnectChanged -> {
        val next = if (message.enabled) state.connecting() else state.with()
        next.state.with(next.effects + ConnectionEffect.EditSettings(AutoConnect(message.enabled)))
    }

    ConnectionMessage.GameSearching -> state.watchReported(ConnectionStatus.Searching)
    is ConnectionMessage.GameConnected -> state.watchReported(ConnectionStatus.Connected(message.pid, message.edition))
    is ConnectionMessage.GameRefused -> state.watchReported(ConnectionStatus.Refused(message.executableName, message.reason))
    is ConnectionMessage.WatchFailed ->
        ConnectionState(ConnectionStatus.Unavailable(message.reason), state.target).with()
}

private fun ConnectionState.connecting(): Transition<ConnectionState, ConnectionEffect> = when {
    status.watching -> with()
    target == null -> copy(status = ConnectionStatus.NoWorkspace).with()
    else -> copy(status = ConnectionStatus.Searching).with(ConnectionEffect.Watch(target))
}

private fun ConnectionState.watchReported(status: ConnectionStatus): Transition<ConnectionState, ConnectionEffect> =
    if (this.status.watching) copy(status = status).with() else with()
