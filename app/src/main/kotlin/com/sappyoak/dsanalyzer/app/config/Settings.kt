package com.sappyoak.dsanalyzer.app.config

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.domain.GameIdentity
import com.sappyoak.dsanalyzer.domain.GameVersion

@Serializable
data class Settings(
    val version: Int = CURRENT_VERSION,
    /** Whether the tool keeps its own files */
    val dataPath: String? = null,
    /**
     * Per-installation state, keyed by [GameIdentity.pathSegment]
     *
     * Keyed on identity rather than on path so a moved installation keeps its state,
     * and a modded copy alongside a stock one is correctly treated as separate
     */
    val installations: Map<String, InstallationSettings> = emptyMap(),
    val activeInstallation: String? = null,
    val overlay: OverlaySettings = OverlaySettings(),
    val window: WindowSettings = WindowSettings(),
    val hotkeys: HotKeySettings = HotKeySettings()
) {
    val active: InstallationSettings? get() = activeInstallation?.let { installations[it] }

    fun withInstallation(key: String, block: (InstallationSettings) -> InstallationSettings): Settings {
        val existing = installations[key] ?: InstallationSettings()
        return copy(installations = installations + (key to block(existing)))
    }

    companion object {
        const val CURRENT_VERSION: Int = 1
    }
}

/**
 * State belonging to one installation.
 *
 * Separate from [Settings] so switching games preserves both rather than overwriting one.
 */
@Serializable
data class InstallationSettings(
    val gamePath: String? = null,
    val version: GameVersion? = null,
    val buildId: String? = null,
    /** Overridden only when the cache is put somewhere other than the data directory */
    val extractedPath: String? = null
)

@Serializable
data class OverlaySettings(
    val enabled: Boolean = false,
    val opacity: Float = 0.5f,
    val drawRadius: Float = 60f
)

@Serializable
data class WindowSettings(
    val dimensions: Dimensions = Dimensions(1100, 750),
    val restoreLastState: Boolean = true,
    val lastDimensions: Dimensions? = null,
    val lastX: Int? = null,
    val lastY: Int? = null,
    val lastPlacement: String? = null
) {
    @Serializable
    class Dimensions(val width: Int, val height: Int)

    val targetDimensions: Dimensions get() {
        val target = if (restoreLastState) lastDimensions else dimensions
        return target ?: dimensions
    }
}
