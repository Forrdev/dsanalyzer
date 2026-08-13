package com.sappyoak.dsanalyzer.app.state

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.app.config.HotKeyBinding

typealias DispatchFn = (Action) -> Unit

/**
 * Pure typed actions describing all the ways the app state can change
 */
@Serializable
sealed interface Action {
    @Serializable
    sealed interface HotKey : Action {
        @Serializable
        data class Bound(val binding: HotKeyBinding, val keyCode: Long) : HotKey
        @Serializable
        data object ResetToDefaults : HotKey
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
    fun bindHotKey(binding: HotKeyBinding, keyCode: Long) =
        dispatch(Action.HotKey.Bound(binding, keyCode))

    fun resetHotKeys() = dispatch(Action.HotKey.ResetToDefaults)


    fun reportProblem(message: String) = dispatch(Action.ProblemReported(message))
}