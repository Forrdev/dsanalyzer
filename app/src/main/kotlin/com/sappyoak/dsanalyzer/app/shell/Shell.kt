package com.sappyoak.dsanalyzer.app.shell

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

import com.sappyoak.dsanalyzer.app.nav.Navigator
import com.sappyoak.dsanalyzer.app.nav.View
import com.sappyoak.dsanalyzer.app.runtime.RuntimeState
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val NAVIGATOR_WIDTH = 300.dp

@Composable
internal fun Shell(
    workspace: String,
    installation: String?,
    navigator: Navigator,
    runtimeState: StateFlow<RuntimeState>,
    onSettings: () -> Unit,
    navigatorPane: @Composable ColumnScope.(View) -> Unit,
    detailPane: @Composable (View) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(AnalyzerTheme.colors.window)
    ) {
        LiveStrip(runtimeState)

        SectionBar(
            workspace = workspace,
            installation = installation,
            navigator = navigator,
            onSettings = onSettings
        )

        Row(modifier = Modifier.weight(1f).fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .width(NAVIGATOR_WIDTH)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.panel)
            ) {
                navigatorPane(navigator.current)
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(AnalyzerTheme.colors.border)
            )

            Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
                DetailBar(navigator)
                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    detailPane(navigator.current)
                }
            }
        }
    }
}

/** Collects the runtime state itself, so a tick recomposes the strip and not the shell */
@Composable
private fun LiveStrip(runtime: StateFlow<RuntimeState>) {
    val state by runtime.collectAsState()
    RuntimeStrip(state)
}
