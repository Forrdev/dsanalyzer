package com.sappyoak.dsanalyzer.app.workspace.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.sappyoak.dsanalyzer.app.maps.MapsMessage
import com.sappyoak.dsanalyzer.app.maps.MapsStore
import com.sappyoak.dsanalyzer.app.maps.ui.MapsScreen
import com.sappyoak.dsanalyzer.app.runtime.RuntimeStore
import com.sappyoak.dsanalyzer.app.runtime.ui.RuntimeScreen
import com.sappyoak.dsanalyzer.app.scripts.ScriptsMessage
import com.sappyoak.dsanalyzer.app.scripts.ScriptsStore
import com.sappyoak.dsanalyzer.app.scripts.ui.ScriptsScreen
import com.sappyoak.dsanalyzer.app.workspace.Workspace
import com.sappyoak.dsanalyzer.app.workspace.WorkspaceTab
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.world.WorldRef

@Composable
public fun WorkspaceShell(
    workspace: Workspace,
    installation: Installation?,
    maps: MapsStore,
    scripts: ScriptsStore,
    runtime: RuntimeStore
) {
    var tab by remember { mutableStateOf(WorkspaceTab.Maps) }

    LaunchedEffect(installation?.id) {
        installation?.let {
            maps.dispatch(MapsMessage.Opened(it))
            scripts.dispatch(ScriptsMessage.Opened(it))
        }
    }

    val follow: (WorldRef) -> Unit = { ref ->
        when (ref) {
            is WorldRef.ScriptEvent -> {
                scripts.dispatch(ScriptsMessage.Navigated(ref))
                tab = WorkspaceTab.Scripts
            }

            is WorldRef.EventFlag -> {
                scripts.dispatch(ScriptsMessage.FlagRequested(ref.flagId))
                tab = WorkspaceTab.Scripts
            }

            else -> {
                maps.dispatch(MapsMessage.Navigated(ref))
                tab = WorkspaceTab.Maps
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Header(workspace, installation)
        PrimaryTabRow(selectedTabIndex = tab.ordinal) {
            WorkspaceTab.entries.forEach { entry ->
                Tab(
                    selected = entry == tab,
                    onClick = { tab = entry },
                    text = { Text(entry.label) }
                )
            }
        }
        HorizontalDivider()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (installation == null) {
                Text("Installation unavailable", style = MaterialTheme.typography.bodyMedium)
            } else {
                when (tab) {
                    WorkspaceTab.Maps -> {
                        val state by maps.state.collectAsState()
                        MapsScreen(state, maps)
                    }

                    WorkspaceTab.Scripts -> {
                        val state by scripts.state.collectAsState()
                        ScriptsScreen(state, scripts, follow)
                    }

                    WorkspaceTab.Runtime -> {
                        val state by runtime.state.collectAsState()
                        RuntimeScreen(state, follow)
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(workspace: Workspace, installation: Installation?) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(workspace.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = installation?.let { "${it.build.edition.name} - ${it.root}" } ?: "Installation Unavailable",
            style = MaterialTheme.typography.bodySmall
        )
    }
}