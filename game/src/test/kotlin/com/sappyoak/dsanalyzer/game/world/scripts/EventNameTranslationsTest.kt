package com.sappyoak.dsanalyzer.game.world.scripts

import kotlinx.serialization.json.Json
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.formats.emevd.EventNames
import com.sappyoak.dsanalyzer.game.bundledText

/** Every distinct name the shipped scripts carry, which is what the bundled file covers */
private const val SHIPPED_NAMES = 1224

private val TRANSLATIONS: Map<String, String> by lazy {
    Json.decodeFromString(bundledText("/definitions/ds1-event-names-en.json"))
}

class EventNameTranslationsTest : FunSpec({
    test("every name the scripts ship is translated exactly once") {
        assertSoftly {
            TRANSLATIONS.size shouldBe SHIPPED_NAMES
            loadEventNameTranslations().size shouldBe SHIPPED_NAMES
        }
    }

    test("no translation is blank and no two names share one") {
        assertSoftly {
            TRANSLATIONS.filterValues { it.isBlank() }.keys.shouldBeEmpty()
            TRANSLATIONS.values.groupingBy { it }.eachCount().filterValues { it > 1 }.keys.shouldBeEmpty()
        }
    }

    /**
     * `XX` and `X` are parameter slots rather than text, so they have to survive into the English.
     * One source name uses full-width `ＸＸ` where every other uses half-width, and that one is
     * deliberately normalised to half-width rather than carried through
     */
    test("parameter placeholders survive translation") {
        TRANSLATIONS
            .filterKeys { "XX" in it || "ＸＸ" in it }
            .filterValues { "XX" !in it }
            .keys.shouldBeEmpty()
    }

    test("a name nothing covers is reported as missing rather than guessed at") {
        loadEventNameTranslations()["存在しないイベント名"] shouldBe null
    }

    test("translating a script's names leaves anything untranslated in Japanese") {
        val names = EventNames(mapOf(0L to "コンストラクタ", 1L to "存在しないイベント名"))

        val translated = names.translatedBy(loadEventNameTranslations())

        assertSoftly {
            translated[0] shouldBe "Constructor"
            translated[1] shouldBe "存在しないイベント名"
            translated.size shouldBe names.size
        }
    }

    test("no translations leaves every name as it was") {
        val names = EventNames(mapOf(0L to "コンストラクタ"))

        names.translatedBy(EventNameTranslations.Empty)[0] shouldBe "コンストラクタ"
    }
})