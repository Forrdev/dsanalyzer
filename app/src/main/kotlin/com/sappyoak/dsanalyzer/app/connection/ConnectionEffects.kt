package com.sappyoak.dsanalyzer.app.connection

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart

import com.sappyoak.dsanalyzer.app.runtime.GameLink
import com.sappyoak.dsanalyzer.app.runtime.RuntimeEffects
import com.sappyoak.dsanalyzer.app.settings.SettingsAccess
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.runtime.session.RuntimeEvent

private val WATCH_KEY = "watch"

public class ConnectionEffects(
    private val settings: SettingsAccess,
    private val link: GameLink
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

        ConnectionEffect.Watch -> link.events.onStart { link.start() }.mapNotNull(::toMessage)
        ConnectionEffect.StopWatching -> flow { link.stop() }
        is ConnectionEffect.EditSettings -> flow { settings.edit(effect.edit) }
    }

    override fun onFailure(effect: ConnectionEffect, failure: Throwable): ConnectionMessage? =
        if (effect is ConnectionEffect.Watch) ConnectionMessage.WatchFailed(failure.message ?: failure.toString())
        else null


    private fun toMessage(event: RuntimeEvent): ConnectionMessage? = when (event) {
        RuntimeEvent.Searching -> ConnectionMessage.GameSearching
        is RuntimeEvent.Attached -> ConnectionMessage.GameConnected(
            pid = event.game.process.pid,
            edition = event.game.edition
        )

        is RuntimeEvent.Refused -> ConnectionMessage.GameRefused(event.game.process.executableName, event.reason)

        is RuntimeEvent.Unsupported -> ConnectionMessage.GameRefused(
            executableName = event.game.process.executableName,
            reason = "${event.game.edition.name} is not read yet"
        )

        is RuntimeEvent.Failed -> ConnectionMessage.WatchFailed(event.reason)
        is RuntimeEvent.Sampled -> null
    }
}

public sealed interface ConnectionEffect {
    public data object CheckAutoConnect : ConnectionEffect
    public data object Watch : ConnectionEffect
    public data object StopWatching : ConnectionEffect

    public data class EditSettings(public val edit: SettingsEdit) : ConnectionEffect
}