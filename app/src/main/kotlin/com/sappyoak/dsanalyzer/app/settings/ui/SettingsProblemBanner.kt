package com.sappyoak.dsanalyzer.app.settings.ui

import androidx.compose.runtime.Composable

import com.sappyoak.dsanalyzer.app.settings.SettingsProblem
import com.sappyoak.dsanalyzer.app.ui.NoticeBanner

@Composable
public fun SettingsProblemBanner(problem: SettingsProblem, onDismiss: () -> Unit) {
    NoticeBanner(listOf(problem.describe()), onDismiss)
}

private fun SettingsProblem.describe(): String = when (this) {
    is SettingsProblem.Recovered ->
        "Settings could not be read and have been reset. The old file is at $backup"
    is SettingsProblem.Unreadable ->
        "Settings could not be loaded, so default are in use: $detail"
    is SettingsProblem.NotSaved ->
        "Settings changes could not be saved and will be lost when you quit: $detail"
}