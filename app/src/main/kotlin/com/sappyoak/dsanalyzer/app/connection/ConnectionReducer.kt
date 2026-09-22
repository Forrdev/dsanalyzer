package com.sappyoak.dsanalyzer.app.connection

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceConnection(
    state: ConnectionState,
    message: ConnectionMessage
): Transition<ConnectionState, ConnectionEffect> = when (message) {
    ConnectionMessage.Start -> state.with(ConnectionEffect.CheckAutoConnect)

    ConnectionMessage.ConnectRequested -> state.connecting()

    ConnectionMessage.DisconnectRequested -> ConnectionState(ConnectionStatus.Off)
        .with(ConnectionEffect.StopWatching)

    is ConnectionMessage.AutoConnectChanged -> {
        val next = if (message.enabled) state.connecting() else state.with()
        next.state.with(next.effects + ConnectionEffect.EditSettings(AutoConnect(message.enabled)))
    }

    ConnectionMessage.GameSearching -> state.watchReported(ConnectionStatus.Searching)
    is ConnectionMessage.GameConnected -> state.watchReported(ConnectionStatus.Connected(message.pid, message.edition))
    is ConnectionMessage.GameRefused -> state.watchReported(ConnectionStatus.Refused(message.executableName, message.reason))
    is ConnectionMessage.WatchFailed -> ConnectionState(ConnectionStatus.Unavailable(message.reason)).with()
}

private fun ConnectionState.connecting(): Transition<ConnectionState, ConnectionEffect> =
    if (status.watching) with()
    else ConnectionState(ConnectionStatus.Searching).with(ConnectionEffect.Watch)

private fun ConnectionState.watchReported(status: ConnectionStatus): Transition<ConnectionState, ConnectionEffect> =
    if (this.status.watching) ConnectionState(status).with() else with()