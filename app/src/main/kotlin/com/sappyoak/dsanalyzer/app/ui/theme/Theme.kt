package com.sappyoak.dsanalyzer.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
public fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppColors provides DarkPalette,
        LocalAppText provides DefaultText
    ) {
        MaterialTheme(
            colorScheme = materialScheme(DarkPalette),
            typography = AppTypography
        ) {
            Surface(
                color = DarkPalette.window,
                contentColor = DarkPalette.textHi,
                content = content
            )
        }
    }
}

public object AnalyzerTheme {
    public val colors: AppColors
        @Composable @ReadOnlyComposable get() = LocalAppColors.current

    public val text: AppText
        @Composable @ReadOnlyComposable get() = LocalAppText.current
}

public val LocalAppColors: ProvidableCompositionLocal<AppColors> =
    staticCompositionLocalOf { DarkPalette }

public val LocalAppText: ProvidableCompositionLocal<AppText> =
    staticCompositionLocalOf { DefaultText }

/**
 * The palette mapped onto the slots material's own components read
 */
private fun materialScheme(colors: AppColors) = darkColorScheme(
    primary = colors.accent,
    onPrimary = colors.window,
    secondary = colors.link,
    onSecondary = colors.window,
    background = colors.window,
    onBackground = colors.textHi,
    surface = colors.panel,
    onSurface = colors.textHi,
    surfaceVariant = colors.raised,
    onSurfaceVariant = colors.textMid,
    error = colors.failed,
    onError = colors.window,
    outline = colors.accent,
    outlineVariant = colors.border
)
