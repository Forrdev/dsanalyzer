package com.sappyoak.dsanalyzer.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
public data class AppColors(
    public val window: Color,
    public val chrome: Color,
    public val panel: Color,
    public val raised: Color,
    public val border: Color,
    public val accent: Color,
    public val textHi: Color,
    public val textMid: Color,
    public val textLo: Color,
    public val resident: Color,
    public val pending: Color,
    public val absent: Color,
    public val failed: Color,
    public val link: Color,
    public val written: Color
)

public val DarkPalette: AppColors = AppColors(
    window = Color(0xFF0E1013),
    chrome = Color(0xFF11141A),
    panel = Color(0xFF15181D),
    raised = Color(0xFF1C2128),
    border = Color(0xFF2A313A),
    accent = Color(0xFFC9A227),
    textHi = Color(0xFFE6EAF0),
    textMid = Color(0xFFA8B2C0),
    textLo = Color(0xFF8A94A3),
    resident = Color(0xFF5FBF7F),
    pending = Color(0xFFE0A458),
    absent = Color(0xFF8A94A3),
    failed = Color(0xFFE36A6C),
    link = Color(0xFF6FAEE0),
    written = Color(0xFFC68AE0)
)
