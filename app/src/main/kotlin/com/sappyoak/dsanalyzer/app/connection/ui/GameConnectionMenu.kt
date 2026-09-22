package com.sappyoak.dsanalyzer.app.connection.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.MenuBarScope

import com.sappyoak.dsanalyzer.app.connection.ConnectionMessage
import com.sappyoak.dsanalyzer.app.connection.ConnectionState
import com.sappyoak.dsanalyzer.app.connection.ConnectionStore
import com.sappyoak.dsanalyzer.app.connection.watching

@Composable
public fun MenuBarScope.GameConnectionMenu(
    state: ConnectionState,
    autoConnect: Boolean,
    store: ConnectionStore
) {
    Menu("Game", mnemonic = 'G') {
        Item("Connect", enabled = !state.status.watching) {
            store.dispatch(ConnectionMessage.ConnectRequested)
        }

        Item("Disconnect", enabled = state.status.watching) {
            store.dispatch(ConnectionMessage.DisconnectRequested)
        }

        Separator()

        CheckboxItem("Connect automatically", checked = autoConnect) {
            store.dispatch(ConnectionMessage.AutoConnectChanged(it))
        }
    }
}