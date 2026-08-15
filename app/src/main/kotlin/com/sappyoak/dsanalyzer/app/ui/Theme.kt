package com.sappyoak.dsanalyzer.app.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.*

object ColorScheme {

    val background = Color(0xFF212121)

    val surface = Color(0xFF282C34)
    val divider = Color(0xFF6B727D)

    val success = Color(0xFF387002)
    val warning = Color(0xFFE5C17C)
    val error = Color(0xFFA000037)

    val accent = Color(0xFF2979FF)
    val secondaryFocus = Color(0xFF383D48)

    val normalText = Color(0xFF979FAD)
}

object Spacing {
    val gapTight = 4.dp
    val gap = 8.dp
    val gapWide = 12.dp
    val gapSection = 20.dp

    val cardPadding = 14.dp
    val screenPadding = 20.dp
}

object Fonts {
    val sans: FontFamily = bundled(
        "fonts/IBMPlexSans-Regular.ttf" to FontWeight.Normal,
        "fonts/IBMPlexSans-Medium.ttf" to FontWeight.Medium,
        "fonts/IBMPlexSans-SemiBold.ttf" to FontWeight.SemiBold
    ) ?: FontFamily.SansSerif

    val mono: FontFamily = bundled(
        "fonts/IBMPlexMono-Regular.ttf" to FontWeight.Normal,
        "fonts/IBMPlexMono-Medium.ttf" to FontWeight.Medium
    ) ?: FontFamily.Monospace

    private fun bundled(vararg entries: Pair<String, FontWeight>): FontFamily? = runCatching {
        FontFamily(entries.map { (resource, weight) -> Font(resource = resource, weight = weight) })
    }.getOrNull()
}

val BaseTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 21.sp,
        lineHeight = 27.sp,
        letterSpacing = (-0.2).sp
    ),

    titleMedium = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 23.sp
    ),

    titleSmall = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.3.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    ),


    bodySmall = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Normal,
        fontSize = 13.5.sp,
        lineHeight = 19.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 17.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
    ),

    displaySmall = TextStyle(
        fontFamily = Fonts.sans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.4).sp
    )
)

object Theme {
    val colors = ColorScheme
    val spacing = Spacing
    val fonts = Fonts
    val typography = BaseTypography
}