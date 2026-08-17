package com.sappyoak.dsanalyzer.app.state.global

import com.sappyoak.dsanalyzer.app.config.HotKeySettings
import com.sappyoak.dsanalyzer.app.state.Action
import com.sappyoak.dsanalyzer.app.state.Store

fun Store.reduceGlobal(state: GlobalState, action: Action): GlobalState = when (action) {
    is Action.HotKey.Bound -> state.copy(
        hotkeys = state.hotkeys.with(action.binding, action.keyCode),
        capturingHotKey = null
    )

    Action.HotKey.ResetToDefaults -> state.copy(
        hotkeys = HotKeySettings(),
        capturingHotKey = null
    )

    Action.HotKey.CaptureCancelled -> state.copy(capturingHotKey = null)
    else -> state
}