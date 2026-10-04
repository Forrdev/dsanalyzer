package com.sappyoak.dsanalyzer.game.world.scripts

import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.game.bundledText

private const val RESOURCE = "/definitions/ds1-event-names-en.json"


public fun loadEventNameTranslations(): EventNameTranslations =
    EventNameTranslations(Json.decodeFromString<Map<String, String>>(bundledText(RESOURCE)))

/**
 * English for the names the scripts ship, which are Japanese and are developer names rather than
 * player-facing text
 *
 * Keyed by the Japanese name rather than by script and event id, because the same name is used for
 * the same thing across scripts — every script has a `コンストラクタ` — and keying by id would
 * repeat each translation once per script and let the copies drift apart.
 */
public class EventNameTranslations(private val byName: Map<String, String>) {
    public val size: Int get() = byName.size

    /** The English for [japanese] or null for a name nothing here covers */
    public operator fun get(japanese: String): String? = byName[japanese]

    public companion object {
        public val Empty: EventNameTranslations = EventNameTranslations(emptyMap())
    }
}

public fun EventNames.translatedBy(translations: EventNameTranslations): EventNames =
    EventNames(all.mapValues { (_, name) -> translations[name] ?: name })