package com.sappyoak.dsanalyzer.app.config

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.sappyoak.dsanalyzer.game.identity.GameBuildId
import kotlinx.serialization.Serializable
import java.awt.GraphicsEnvironment

import com.sappyoak.dsanalyzer.game.identity.GameIdentity
import com.sappyoak.dsanalyzer.game.identity.GameVersion
import com.sappyoak.dsanalyzer.shared.SNullablePath
import com.sappyoak.dsanalyzer.shared.SInstant
import kotlin.time.Instant

@Serializable
data class Settings(
    val version: Int = CURRENT_VERSION,
    /** Whether the tool keeps its own files */
    val dataPath: SNullablePath? = null,
    /**
     * Per-installation state, keyed by [GameIdentity.pathSegment]
     *
     * Keyed on identity rather than on path so a moved installation keeps its state,
     * and a modded copy alongside a stock one is correctly treated as separate
     */
    val installations: Map<String, InstallationSettings> = emptyMap(),
    val activeInstallation: String? = null,

    /**
     * Workspaces to reopen
     *
     * Persisted so a session resumes where it left off
     */
    val workspaces: List<PersistedWorkspace> = emptyList(),
    val activeWorkspace: String? = null,
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
    val gamePath: SNullablePath? = null,
    val version: GameVersion? = null,
    val buildId: GameBuildId? = null,
    /** Overridden only when the cache is put somewhere other than the data directory */
    val extractedPath: SNullablePath? = null,
    val lastScannedAt: SInstant = Instant.DISTANT_PAST
)

@Serializable
data class OverlaySettings(
    val enabled: Boolean = false,
    val opacity: Float = 0.5f,
    val drawRadius: Float = 60f
)

/**
 * A workspace as it survives a restart
 */
@Serializable
data class PersistedWorkspace(
    val id: String,
    val installationKey: String,
    val title: String
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
    data class Dimensions(val width: Int, val height: Int)

    fun getWindowPosition(): WindowPosition {
        return if (restoreLastState && lastX != null && lastY != null && areCoordinatesOnAnyScreen(lastX, lastY)) {
            WindowPosition(lastX.dp, lastY.dp)
        } else WindowPosition.PlatformDefault
    }

    fun getWindowPlacement(): WindowPlacement {
        if (!restoreLastState) return WindowPlacement.Floating
        if (lastPlacement == null) return WindowPlacement.Floating
        return when (lastPlacement) {
            WindowPlacement.Maximized.name -> WindowPlacement.Maximized
            WindowPlacement.Fullscreen.name -> WindowPlacement.Fullscreen
            else -> WindowPlacement.Floating
        }
    }

    val targetDimensions: Dimensions get() {
        val target = if (restoreLastState) lastDimensions else dimensions
        return target ?: dimensions
    }

    private fun areCoordinatesOnAnyScreen(x: Int, y: Int): Boolean {
        val env = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val screens = env.screenDevices
        for (screen in screens) {
            val bounds = screen.defaultConfiguration.bounds
            if (bounds.contains(x, y)) return true
        }
        return false
    }
}
