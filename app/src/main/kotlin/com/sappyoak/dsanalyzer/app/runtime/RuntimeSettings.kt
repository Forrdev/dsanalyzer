package com.sappyoak.dsanalyzer.app.runtime

import com.sappyoak.dsanalyzer.app.settings.Settings
import com.sappyoak.dsanalyzer.app.settings.SettingsEdit
import com.sappyoak.dsanalyzer.runtime.session.CharacterSnapshot
import com.sappyoak.dsanalyzer.runtime.session.ai.AIScope
import kotlinx.serialization.Serializable

@Serializable
public data class RuntimeSettings(
    public val hideDeadCharacters: Boolean = true,
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

internal val Settings.drawsHitWindows: Boolean
    get() = overlay.enabled && overlay.attackHitboxes && runtime.ai.tracking != AITracking.Off


internal fun List<CharacterSnapshot>.listed(hideDead: Boolean): List<CharacterSnapshot> {
    val others = filterNot { it.isPlayer }
    return if (hideDead) others.filter { it.alive } else others.sortedBy { !it.alive }
}

public data class HideDeadCharacters(public val enabled: Boolean) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.copy(
        runtime = settings.runtime.copy(
            hideDeadCharacters = enabled
        )
    )
}

public data class TrackAI(public val tracking: AITracking) : SettingsEdit {
    override fun applyTo(settings: Settings): Settings = settings.copy(
        runtime = settings.runtime.copy(
            ai = settings.runtime.ai.copy(tracking = tracking)
        )
    )
}
