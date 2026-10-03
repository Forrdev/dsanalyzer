package com.sappyoak.dsanalyzer.game.world.text

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.fmg.Fmg
import com.sappyoak.dsanalyzer.formats.fmg.readFmg
import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openBinder
import com.sappyoak.dsanalyzer.game.files.openGameFiles

/** Runs against a real installation when DS1_PTDE_PATH points at one */
private val ROOT: Path? = System.getenv("DS1_PTDE_PATH")?.let(Path::of)

/** Enough entries that a file which parsed into near-nothing is not mistaken for a working read */
private const val PLAUSIBLE_PLACE_NAMES = 20

private fun <T> withInstalledText(block: (GameFiles) -> T): T {
    val root = checkNotNull(ROOT)
    val installation = Installation(
        id = InstallationId.forRoot(root),
        root = root,
        executable = root.resolve(GameEdition.PrepareToDie.executableName),
        build = GameBuild(GameEdition.PrepareToDie)
    )
    return openGameFiles(installation).use(block)
}

class InstalledTextTest : FunSpec({
    test("the installation ships text for more than the language it is set to").config(enabled = ROOT != null) {
        val present = withInstalledText { files -> TextLanguage.entries.filter { files.hasText(it) } }

        println("Languages present: ${present.joinToString { it.folder }}")
        assertSoftly {
            present shouldContain TextLanguage.English
            present shouldContain TextLanguage.Japanese
        }
    }

    test("place names read back as names").config(enabled = ROOT != null) {
        val places = withInstalledText { it.loadText(TextLanguage.English, TextCategory.PlaceNames) }
        val strings = checkNotNull(places).strings

        println("Place names: ${strings.size}, first few ${strings.entries.take(5)}")
        assertSoftly {
            strings.size shouldBeGreaterThan PLAUSIBLE_PLACE_NAMES
            strings.values.none { it.isBlank() } shouldBe true
        }
    }

    /**
     * The override is the half of this that is easy to leave out, so it is worth seeing it do
     * something: the DLC rewrites entries as well as adding them, and a base-only read keeps the
     * pre-DLC string for every one it rewrote
     */
    test("the DLC's overrides change what the base says").config(enabled = ROOT != null) {
        val category = TextCategory.PlaceNames
        val (base, patch) = withInstalledText { files ->
            files.readOne(TextLanguage.English, category.base) to
                    files.readOne(TextLanguage.English, checkNotNull(category.patch))
        }

        val added = patch.strings.keys - base.strings.keys
        val rewritten = patch.strings.filter { (id, text) -> base[id] != null && base[id] != text }

        println("Overrides added ${added.size} place names and rewrote ${rewritten.size}")
        (added.size + rewritten.size) shouldBeGreaterThan 0
    }

    /**
     * The premise of the whole glossary: the same entry id names the same thing in both languages.
     * If the id sets diverge, pairing by id is wrong and the overlap is what says by how much
     */
    test("Japanese and English name the same ids").config(enabled = ROOT != null) {
        val (japanese, english) = withInstalledText { files ->
            files.loadText(TextLanguage.Japanese, TextCategory.naming) to
                    files.loadText(TextLanguage.English, TextCategory.naming)
        }

        assertSoftly {
            TextCategory.naming.forEach { category ->
                val ja = checkNotNull(japanese[category]).strings
                val en = checkNotNull(english[category]).strings
                val shared = ja.keys intersect en.keys

                println("${category.label}: ${ja.size} ja, ${en.size} en, ${shared.size} shared")
                withClue(category.label) { shared.shouldNotBeEmpty() }
            }
        }
    }
})

/** one string file on its own, overrides not applied */
private fun GameFiles.readOne(language: TextLanguage, ref: FmgRef): Fmg {
    val container = checkNotNull(openBinder(language.pathTo(ref.archive))) { "No ${ref.archive} for $language" }
    val entry = checkNotNull(container.binder[ref.fmgId]) { "No FMG ${ref.fmgId} in ${ref.archive}" }
    return readFmg(container.open(entry))
}