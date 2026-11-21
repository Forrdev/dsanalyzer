package com.sappyoak.dsanalyzer.app.logs

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import com.sappyoak.dsanalyzer.app.store.EffectRunner

public class LogsEffects(private val follower: LogFollower) : EffectRunner<LogsEffect, LogsMessage> {
    override fun keyOf(effect: LogsEffect): Any = effect

    override fun execute(effect: LogsEffect): Flow<LogsMessage> = when (effect) {
        LogsEffect.Follow -> follower.follow().map(LogsMessage::Read)
    }

    override fun onFailure(effect: LogsEffect, failure: Throwable): LogsMessage =
        LogsMessage.Failed(failure.message ?: failure.toString())
}

public sealed interface LogsEffect {
    public data object Follow : LogsEffect
}
