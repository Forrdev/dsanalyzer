package com.sappyoak.dsanalyzer.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
public data class AppText(
    public val mono: TextStyle,
    public val monoSmall: TextStyle,
    public val label: TextStyle
)

private const val TABULAR = "tnum"

public val DefaultText: AppText = AppText(
    mono = TextStyle(
        fontFamily = MonoFamily,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        fontFeatureSettings = TABULAR
    ),
    monoSmall = TextStyle(
        fontFamily = MonoFamily,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        fontFeatureSettings = TABULAR
    ),
    label = TextStyle(
        fontFamily = UIFamily,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Medium,
        fontFeatureSettings = TABULAR
    )
)

public val AppTypography: Typography = Typography().let { base ->
    base.copy(
        titleMedium = base.titleMedium.copy(fontFamily = UIFamily, fontSize = 15.sp),
        titleSmall = base.titleSmall.copy(fontFamily = UIFamily, fontSize = 13.sp),
        bodyMedium = base.bodyMedium.copy(fontFamily = UIFamily, fontSize = 13.sp),
        bodySmall = base.bodySmall.copy(fontFamily = UIFamily, fontSize = 12.sp),
        labelLarge = base.labelLarge.copy(fontFamily = UIFamily, fontSize = 13.sp),
        labelMedium = base.labelMedium.copy(fontFamily = UIFamily, fontSize = 11.sp),
        labelSmall = base.labelSmall.copy(fontFamily = UIFamily, fontSize = 10.sp)
    )    
}
