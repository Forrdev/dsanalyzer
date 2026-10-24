package com.sappyoak.dsanalyzer.app.runtime

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope

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


public data class TrackAI(public val tracking: AITracking) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.copy(
        ai = settings.ai.copy(tracking = tracking)
    )
}

internal val AITracking.scope: AIScope
    get() = when (this) {
        AITracking.Off -> AIScope.Off
        AITracking.TargetingPlayer -> AIScope.TargetingPlayer
        AITracking.All -> AIScope.All
    }
