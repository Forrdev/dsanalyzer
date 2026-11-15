package com.sappyoak.dsanalyzer.game.world.params

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.game.bundledText

private const val RESOURCE = "/definitions/ds1-param-row-names-en.json"

/** One English row name, and whether the translations agreed on it */
public data class RowNameTranslation(public val english: String, public val reviewed: Boolean)

/**
 * English for the Japanese names Remastered's param rows carry, which are developer labels. Remastered's
 * params are a superset of Prepare to Die's, so the one file covers both.
 *
 * Two models translated every name. Where they agreed the name is [RowNameTranslation.reviewed]; where
 * they did not, the first model's reading is used and kept for review
 */
public class RowNameTranslations(
    private val accepted: Map<String, String>,
    private val review: Map<String, List<String>>
) {
    public val size: Int get() = accepted.size + review.size

    public operator fun get(japanese: String): RowNameTranslation? {
        val name = japanese.trim()
        accepted[name]?.let { return RowNameTranslation(it, reviewed = true) }
        return review[name]?.firstOrNull()?.let { RowNameTranslation(it, reviewed = false) }
    }

    public companion object {
        public val None: RowNameTranslations = RowNameTranslations(emptyMap(), emptyMap())
    }
}

@Serializable
private class TranslationFile(val accepted: Map<String, String>, val review: Map<String, List<String>>)

private val TRANSLATIONS: RowNameTranslations by lazy {
    Json.decodeFromString<TranslationFile>(bundledText(RESOURCE)).let { RowNameTranslations(it.accepted, it.review) }
}

public fun loadRowNameTranslations(): RowNameTranslations = TRANSLATIONS
