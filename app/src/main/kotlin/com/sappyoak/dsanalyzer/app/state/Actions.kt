package com.sappyoak.dsanalyzer.app.state

import androidx.compose.ui.input.key.*
import java.nio.file.Path
import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.config.HotKeyBinding
import com.sappyoak.dsanalyzer.domain.GameVersion

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
            val freeSpaceBinding: HotKeyBinding,
        )

        @Serializable data object InstallationsRequested : Setup
        @Serializable data class InstallationSelected(val key: String) : Setup
        @Serializable data class InstallationRemoved(val key: String) : Setup

        @Serializable
        data class GameVersionOverridden(val version: GameVersion) : Setup
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
    data class ProblemReported(val message: String) : Action
}

/**
 * A think wrapper over dispatch describing what the screens can ask for. Screen could
 * take a dispatch function directly, but this way keeps every screens signature unchanged, and
 * it gives one place to read what the interface is capable of.
 *
 * Every method is one dispatch. To dispatch multiple actions an effect should be used
 */
class AppActions(private val dispatch: DispatchFn) {
    fun bindHotkey(binding: HotKeyBinding, keyCode: Long) =
        dispatch(Action.HotKey.Bound(binding, keyCode))

    fun resetHotkeys() = dispatch(Action.HotKey.ResetToDefaults)
    fun cancelHotkeyCapture() = dispatch(Action.HotKey.CaptureCancelled)

    fun reportProblem(message: String) = dispatch(Action.ProblemReported(message))
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

