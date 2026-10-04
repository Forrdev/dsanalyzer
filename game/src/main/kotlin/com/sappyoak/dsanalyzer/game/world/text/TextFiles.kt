package com.sappyoak.dsanalyzer.game.world.text

import com.sappyoak.dsanalyzer.formats.binder.OpenBinder
import com.sappyoak.dsanalyzer.formats.fmg.Fmg
import com.sappyoak.dsanalyzer.formats.fmg.readFmg
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openBinder

/**
 * One category's strings in one language, with the DLC's overrides applied over the base
 */
public fun GameFiles.loadText(
    language: TextLanguage,
    category: TextCategory
): Fmg? = loadText(language, listOf(category))[category]

/**
 * Read several categories at once, opening each container once rather than per category
 *
 * Both containers are tens of megabytes and a category can need either or both, so the naive
 * per-category version opens 'menu' ten times to read ten overrides out of it
 */
public fun GameFiles.loadText(
    language: TextLanguage,
    categories: Collection<TextCategory>
): Map<TextCategory, Fmg> {
    val needed = categories.flatMapTo(mutableSetOf()) {
        listOfNotNull(it.base.archive, it.patch?.archive)
    }
    val binders = needed.associateWith { openBinder(language.pathTo(it)) }

    return categories.mapNotNull { category ->
        val base = binders[category.base.archive]?.readFmg(category.base.fmgId) ?: return@mapNotNull null
        val patch = category.patch?.let { binders[it.archive]?.readFmg(it.fmgId) }

        category to if (patch == null) base else base + patch
    }.toMap()
}

/** Whether this language's text is present at all */
public fun GameFiles.hasText(language: TextLanguage): Boolean =
    TextArchive.entries.all { exists(language.pathTo(it)) }

private fun OpenBinder.readFmg(fmgId: Int): Fmg? = binder[fmgId]?.let { readFmg(open(it)) }