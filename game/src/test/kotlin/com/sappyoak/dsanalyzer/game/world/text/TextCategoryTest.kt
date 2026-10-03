package com.sappyoak.dsanalyzer.game.world.text

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

import com.sappyoak.dsanalyzer.game.files.GamePath

class TextCategoryTest : FunSpec({
    test("text lives under the language's own folder") {
        assertSoftly {
            TextLanguage.English.pathTo(TextArchive.Item) shouldBe
                    GamePath.of("/msg/ENGLISH/item.msgbnd.dcx")
            TextLanguage.Japanese.pathTo(TextArchive.Menu) shouldBe
                    GamePath.of("/msg/JAPANESE/menu.msgbnd.dcx")
        }
    }

    /**
     * Two categories reading the same file would each be showing the other's text under its own
     * name, which looks like working until someone notices rings named after spells. The ids are
     * transcribed by hand, so a duplicate is one keystroke away
     */
    test("no two categories read the same file") {
        assertSoftly {
            TextCategory.entries.map { it.base }.duplicates().shouldBeEmpty()
            TextCategory.entries.mapNotNull { it.patch }.duplicates().shouldBeEmpty()
        }
    }

    /** Every override the game ships is in `menu`, whatever container the base is in */
    test("overrides all live in the menu container") {
        TextCategory.entries.mapNotNull { it.patch }.map { it.archive }.distinct() shouldContainExactly
                listOf(TextArchive.Menu)
    }

    /**
     * Feature names are the one naming file with nothing overriding them. Asserted rather than
     * left implicit, because a null here otherwise reads as a row somebody forgot to finish
     */
    test("the one category with no override is the one the game gives none") {
        TextCategory.entries.filter { it.patch == null } shouldContainExactly listOf(TextCategory.FeatureNames)
    }

    test("a glossary is built from the files that name one thing each") {
        assertSoftly {
            TextCategory.naming shouldContainExactly listOf(
                TextCategory.ItemNames,
                TextCategory.WeaponNames,
                TextCategory.ArmorNames,
                TextCategory.AccessoryNames,
                TextCategory.SpellNames,
                TextCategory.FeatureNames,
                TextCategory.NpcNames,
                TextCategory.PlaceNames
            )
            TextCategory.naming.map { it.base.archive }.distinct() shouldContainExactly listOf(TextArchive.Item)
        }
    }
})

private fun <T> List<T>.duplicates(): List<T> =
    groupingBy { it }.eachCount().filterValues { it > 1 }.keys.toList()