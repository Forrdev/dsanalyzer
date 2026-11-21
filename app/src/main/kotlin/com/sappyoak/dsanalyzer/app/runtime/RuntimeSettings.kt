package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import kotlinx.serialization.Serializable

@Serializable
public data class RuntimeSettings(
    public val ai: AISettings = AISettings()
)

@Serializable
public enum class AITracking {
    Off,
    TargetingPlayer,
    All;
}

@Serializable
public data class AISettings(
    public val tracking: AITracking = AITracking.TargetingPlayer
)

internal val AITracking.scope: AIScope
    get() = when (this) {
        AITracking.Off -> AIScope.Off
        AITracking.TargetingPlayer -> AIScope.TargetingPlayer
        AITracking.All -> AIScope.All
    }

/** Attack windows only mean something for characters whose AI is read */
internal val Settings.wantsTimings: Boolean
    get() = runtime.ai.tracking != AITracking.Off

/** The dead stay in the block lists until the block unloads, so every list passes over them */
internal fun List<CharacterSnapshot>.living(): List<CharacterSnapshot> = filter { it.alive }

public data class TrackAI(public val tracking: AITracking) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.copy(
        runtime = settings.runtime.copy(
            ai = settings.runtime.ai.copy(tracking = tracking)
        )
    )
}
