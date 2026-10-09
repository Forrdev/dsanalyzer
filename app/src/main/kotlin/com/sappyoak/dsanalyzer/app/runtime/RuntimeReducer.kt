package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

/** Enough flag changes to see a pattern, few enough that the list stays scrollable */
private const val LOG_LIMIT = 200

public fun reduceRuntime(
    state: RuntimeState,
    message: RuntimeMessage
): Transition<RuntimeState, RuntimeEffect> = when (message) {
    RuntimeMessage.Opened -> state.with(RuntimeEffect.Observe)
    RuntimeMessage.PlacedEnemiesRequested -> state.with(RuntimeEffect.RequestPlacedEnemies)
    is RuntimeMessage.LinkChanged -> state.copy(
        attached = message.link.attached,
        snapshot = message.link.snapshot,
        flagLog = state.logAfter(message.link),
        problem = message.link.note,
    ).with()
}

private fun RuntimeState.logAfter(link: LinkState): List<FlagEntry> {
    val carried = if (link.attached != null && link.attached == attached) flagLog else emptyList()
    val snapshot = link.snapshot ?: return carried
    if (snapshot.flagChanges.isEmpty()) return carried

    val added = snapshot.flagChanges.map { FlagEntry(it.flagId, it.set, snapshot.frame, snapshot.place) }
    return (added + carried).take(LOG_LIMIT)
}