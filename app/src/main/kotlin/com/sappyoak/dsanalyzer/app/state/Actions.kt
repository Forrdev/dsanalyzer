package com.sappyoak.dsanalyzer.app.state

import androidx.compose.ui.input.key.*
import java.nio.file.Path
import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.config.HotKeyBinding
import com.sappyoak.dsanalyzer.domain.GameVersion
import com.sappyoak.dsanalyzer.domain.Problem

typealias DispatchFn = (Action) -> Unit

/**
 * Pure typed actions describing all the ways the app state can change
 */
@Serializable
sealed interface Action {

    @Serializable
    sealed interface Setup : Action {

        @Serializable data object GamePathRequested : Setup
        @Serializable data class GamePathChosen(val path: Path) : Setup

        @Serializable data object DataPathRequested : Setup
        @Serializable
        data class DataPathChosen(
            val path: Path,
            val freeSpaceBytes: Long,
            val writable: Boolean
        ) : Setup


        @Serializable data object ExtractPathRequested : Setup
        @Serializable
        data class ExtractPathChosen(
            val path: Path,
            val freeSpaceBytes: Long
        ) : Setup

        @Serializable data object InstallationsRequested : Setup
        @Serializable data class InstallationsListed(val installations: List<InstallationEntry>) : Setup
        @Serializable data class InstallationSelected(val key: String) : Setup
        @Serializable data class InstallationRemoved(val key: String) : Setup

        @Serializable
        data class GameVersionOverridden(val version: GameVersion) : Setup

        @Serializable data object CacheSizeRequested : Setup
        @Serializable data class CacheSizeMeasured(val bytes: Long) : Setup
    }

    @Serializable
    sealed interface HotKey : Action {
        @Serializable
        data class Bound(val binding: HotKeyBinding, val keyCode: Long) : HotKey
        @Serializable
        data object ResetToDefaults : HotKey
        @Serializable
        data object CaptureCancelled : HotKey
    }

    @Serializable
    data class ProblemReported(val problem: Problem) : Action
}

/**
 * A think wrapper over dispatch describing what the screens can ask for. Screen could
 * take a dispatch function directly, but this way keeps every screens signature unchanged, and
 * it gives one place to read what the interface is capable of.
 *
 * Every method is one dispatch. To dispatch multiple actions an effect should be used
 */
class AppActions(val dispatch: DispatchFn) {
    fun chooseGamePath() = dispatch(Action.Setup.GamePathRequested)
    fun useGamePath(path: Path) = dispatch(Action.Setup.GamePathChosen(path))

    fun chooseDataPath() = dispatch(Action.Setup.DataPathRequested)
    fun useDataPath(path: Path, freeSpaceBytes: Long, writable: Boolean) {
        dispatch(Action.Setup.DataPathChosen(path, freeSpaceBytes, writable))
    }

    fun chooseExtractPath() = dispatch(Action.Setup.ExtractPathRequested)
    fun useExtractPath(path: Path, freeSpaceBytes: Long) {
        dispatch(Action.Setup.ExtractPathChosen(path, freeSpaceBytes))
    }

    fun browseInstallations() = dispatch(Action.Setup.InstallationsRequested)

    fun selectInstallation(key: String) {
        dispatch(Action.Setup.InstallationSelected(key))
    }
    fun removeInstallation(key: String) {
        dispatch(Action.Setup.InstallationRemoved(key))
    }

    fun overrideGameVersion(version: GameVersion) {
        dispatch(Action.Setup.GameVersionOverridden(version))
    }

    fun bindHotkey(binding: HotKeyBinding, keyCode: Long) =
        dispatch(Action.HotKey.Bound(binding, keyCode))

    fun resetHotkeys() = dispatch(Action.HotKey.ResetToDefaults)
    fun cancelHotkeyCapture() = dispatch(Action.HotKey.CaptureCancelled)

    fun reportProblem(problem: Problem) = dispatch(Action.ProblemReported(problem))
}

fun AppActions.handleKeyEvent(event: KeyEvent, capturingHotKey: HotKeyBinding? = null): Boolean {
    if (event.type != KeyEventType.KeyDown) return false
    if (capturingHotKey != null) {
        val keyCode = event.key.keyCode
        if (keyCode == Key.Escape.keyCode) {
            cancelHotkeyCapture()
            return true
        }

        bindHotkey(capturingHotKey, keyCode)
        return true
    }

    return false
}

