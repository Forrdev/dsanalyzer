package com.sappyoak.dsanalyzer.app.ui


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.sappyoak.dsanalyzer.app.state.AppActions
import com.sappyoak.dsanalyzer.app.state.AppState


@Composable
fun AppShell(
    state: AppState,
    actions: AppActions
) {
    MaterialTheme(typography = BaseTypography) {
        Scaffold { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                "meow"
            }
        }
    }
}