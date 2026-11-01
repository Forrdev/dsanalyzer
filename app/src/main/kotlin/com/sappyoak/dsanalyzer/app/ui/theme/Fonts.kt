package com.sappyoak.dsanalyzer.app.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font as loadFont
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {  }

private const val FONT_DIR = "/fonts/"

private object FontResources

public val MonoFamily: FontFamily = family(
    name = "JetBrains Mono",
    faces = listOf(
        "JetBrainsMono-Regular.ttf" to FontWeight.Normal,
        "JetBrainsMono-Medium.ttf" to FontWeight.Medium,
        "JetBrainsMono-Bold.ttf" to FontWeight.Bold
    )
) ?: FontFamily.Monospace


public val UIFamily: FontFamily = family(
    name = "Inter",
    faces = listOf(
        "Inter-Regular.ttf" to FontWeight.Normal,
        "Inter-Medium.ttf" to FontWeight.Medium,
        "Inter-SemiBold.ttf" to FontWeight.SemiBold
    )
) ?: FontFamily.SansSerif

private fun family(
    name: String,
    faces: List<Pair<String, FontWeight>>
): FontFamily? {
    val loaded = mutableListOf<Font>()

    for ((file, weight) in faces) {
        val face = read(file, weight)
        if (face == null) {
            logger.warn {
                "$name is not bundled ($FONT_DIR$file is absent), falling back to a system font"
            }
            return null
        }
        logger.debug {
            "loaded $name from $FONT_DIR$file"
        }
        loaded.add(face)
    }

    return FontFamily(loaded)
}

private fun read(file: String, weight: FontWeight): Font? =
    FontResources::class.java.getResourceAsStream("$FONT_DIR$file")?.use { stream ->
        loadFont(file, stream.readBytes(), weight, FontStyle.Normal)
    }
