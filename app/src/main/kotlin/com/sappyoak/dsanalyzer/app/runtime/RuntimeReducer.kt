package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.runtime.timing.TimingRequest
import com.sappyoak.dsanalyzer.app.runtime.timing.requestsFor
import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

/** Enough flag changes to see a pattern, few enough that the list stays scrollable */
private const val LOG_LIMIT = 200

public fun reduceRuntime(
    state: RuntimeState,
    message: RuntimeMessage
): Transition<RuntimeState, RuntimeEffect> = when (message) {
    is RuntimeMessage.Opened -> state.copy(installation = message.installation)
        .with(RuntimeEffect.Observe, RuntimeEffect.FollowAITracking, RuntimeEffect.FollowTimingsWanted)
    RuntimeMessage.PlacedEnemiesRequested -> state.with(RuntimeEffect.RequestPlacedEnemies)
    RuntimeMessage.CollisionRequested -> state.with(RuntimeEffect.RequestCollision)
    RuntimeMessage.CharacterEffectsRequested -> state.with(RuntimeEffect.RequestCharacterEffects)

    is RuntimeMessage.LinkChanged -> state.copy(
        attached = message.link.attached,
        snapshot = message.link.snapshot,
        flagLog = state.logAfter(message.link),
        placedEnemies = message.link.placedEnemies,
        collision = message.link.collision,
        characterEffects = message.link.characterEffects,
        problem = message.link.note,
    ).withTimingRequests()

    is RuntimeMessage.AnimationTargetChosen -> state.copy(animationTarget = message.target).with(RuntimeEffect.Animate(message.target))
    is RuntimeMessage.TimingsWanted -> state.copy(timings = state.timings.copy(wanted = message.wanted))
        .withTimingRequests()

    is RuntimeMessage.TimingsLoaded -> state.copy(timings = state.timings.loaded(message.models, message.map))
        .withTimingRequests()
}

private fun RuntimeState.withTimingRequests(): Transition<RuntimeState, RuntimeEffect> {
    val installation = installation ?: return this.with()
    val (library, requests) = timings.requestsFor(snapshot)
    val effects = requests.map { request ->
        when (request) {
            is TimingRequest.ForMap -> RuntimeEffect.LoadMapTimings(installation, request.map, request.known)
            is TimingRequest.ForModels -> RuntimeEffect.LoadModelTimings(installation, request.models)
        }
    }

    return copy(timings = library).with(effects)
}

private fun RuntimeState.logAfter(link: LinkState): List<FlagEntry> {
    val carried = if (link.attached != null && link.attached == attached) flagLog else emptyList()
    val snapshot = link.snapshot ?: return carried

    // the link changes for more than ticks. If a collision or roster answer arrives beside the tick already logged
    // and logging its changes again would list every one of them twice
    if (snapshot === this.snapshot || snapshot.flagChanges.isEmpty()) return carried

    val added = snapshot.flagChanges.map { FlagEntry(it.flagId, it.set, snapshot.frame, snapshot.place) }
    return (added + carried).take(LOG_LIMIT)
}
