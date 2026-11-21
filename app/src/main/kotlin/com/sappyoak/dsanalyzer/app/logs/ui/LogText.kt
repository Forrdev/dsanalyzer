package com.sappyoak.dsanalyzer.app.logs.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

import com.sappyoak.dsanalyzer.app.logs.LogLevel
import com.sappyoak.dsanalyzer.app.ui.theme.AnalyzerTheme

private val CLOCK = DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withZone(ZoneId.systemDefault())
private val DATED = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())

internal fun Instant.clock(): String = CLOCK.format(this)

internal fun Instant.dated(): String = DATED.format(this)

internal val LogLevel.tag: String get() = name.uppercase()

@Composable
internal fun LogLevel.color(): Color = when (this) {
    LogLevel.Error -> AnalyzerTheme.colors.failed
    LogLevel.Warn -> AnalyzerTheme.colors.pending
    LogLevel.Info -> AnalyzerTheme.colors.textMid
    LogLevel.Debug, LogLevel.Trace -> AnalyzerTheme.colors.textLo
}
