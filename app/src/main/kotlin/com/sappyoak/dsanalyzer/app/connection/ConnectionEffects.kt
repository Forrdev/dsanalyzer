package com.sappyoak.dsanalyzer.app.connection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.settings.SettingsAccess
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.native.process.Processes
import com.sappyoak.dsanalyzer.runtime.ConnectionEvent
import com.sappyoak.dsanalyzer.runtime.watchForGame

private val WATCHED_EDITIONS = GameEdition.entries.toSet()
private val WATCH_KEY = "watch"

public class ConnectionEffects(
    private val settings: SettingsAccess,
    private val processes: () -> Processes
) : EffectRunner<ConnectionEffect, ConnectionMessage> {
    /** Starting and stopping share a key, so either one cancels a running watch */
    override fun keyOf(effect: ConnectionEffect): Any? = when (effect) {
        ConnectionEffect.Watch, ConnectionEffect.StopWatching -> WATCH_KEY
        else -> null
    }

    override fun execute(effect: ConnectionEffect): Flow<ConnectionMessage> = when (effect) {
        ConnectionEffect.CheckAutoConnect -> flow {
            if (settings.loaded().connection.autoConnect) emit(ConnectionMessage.ConnectRequested)
        }

        ConnectionEffect.Watch -> processes().watchForGame(WATCHED_EDITIONS).map(::toMessage)
        ConnectionEffect.StopWatching -> emptyFlow()
        is ConnectionEffect.EditSettings -> flow { settings.edit(effect.edit) }
    }

    override fun onFailure(effect: ConnectionEffect, failure: Throwable): ConnectionMessage? =
        if (effect is ConnectionEffect.Watch) ConnectionMessage.WatchFailed(failure.message ?: failure.toString())
        else null

    private fun toMessage(event: ConnectionEvent): ConnectionMessage = when (event) {
        ConnectionEvent.Searching -> ConnectionMessage.GameSearching
        is ConnectionEvent.Connected -> event.connection.game.let {
            ConnectionMessage.GameConnected(it.process.pid, it.edition)
        }
        is ConnectionEvent.Refused -> ConnectionMessage.GameRefused(event.game.process.executableName, event.reason)
    }
}

public sealed interface ConnectionEffect {
    public data object CheckAutoConnect : ConnectionEffect
    public data object Watch : ConnectionEffect
    public data object StopWatching : ConnectionEffect

    public data class EditSettings(public val edit: SettingsEdit) : ConnectionEffect
}