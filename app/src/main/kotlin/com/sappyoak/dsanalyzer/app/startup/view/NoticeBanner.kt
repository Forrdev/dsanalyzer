package com.sappyoak.dsanalyzer.app.startup.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sappyoak.dsanalyzer.app.startup.StartupNotice


@Composable
public fun NoticeBanner(
    notices: List<StartupNotice>,
    onDismiss: () -> Unit
) {
   Card(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
       Column(
           modifier = Modifier.padding(12.dp),
           verticalArrangement = Arrangement.spacedBy(4.dp)
       ) {
           notices.forEach { Text(it.describe()) }
           TextButton(onClick = onDismiss) { Text("Dismiss") }
       }
   }
}

private fun StartupNotice.describe(): String = when (this) {
    is StartupNotice.SettingsRecovered ->
        "Settings could not be read and have been reset. The old file is at $backup"

    is StartupNotice.SettingsUnreadable ->
        "Settings could not be loaded, so defaults are in use: $detail"

    is StartupNotice.SettingsNotSaved ->
        "Changes could not be saved and will be lost when you quit: $detail"

    is StartupNotice.WorkspacesUnreadable ->
        "Existing workspaces could not be listed: $detail"

    is StartupNotice.WorkspaceNotCreated ->
        "The workspace \"$name\" could not be created: $detail"

    is StartupNotice.InstallationsUncheckable ->
        "Saved installations could not be checked: $detail"

    is StartupNotice.FolderNotInspected ->
        "$folder could not be checked: $detail"
}