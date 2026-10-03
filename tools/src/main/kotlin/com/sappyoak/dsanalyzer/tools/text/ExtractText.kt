package com.sappyoak.dsanalyzer.tools.text

import java.nio.file.Files
import java.nio.file.Path
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import com.sappyoak.dsanalyzer.game.GameBuild
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.game.world.scripts.availableScripts
import com.sappyoak.dsanalyzer.game.world.scripts.loadScriptNames
import com.sappyoak.dsanalyzer.game.world.text.TextCategory
import com.sappyoak.dsanalyzer.game.world.text.TextLanguage
import com.sappyoak.dsanalyzer.game.world.text.hasText
import com.sappyoak.dsanalyzer.game.world.text.loadText

private const val TERMS_FILE = "ja-en-terms.json"
private const val EVENT_NAMES_FILE = "event-names.json"

private val JSON = Json { prettyPrint = true }

/**
 * Pulls the text a translation needs out of an installation
 *
 * The term pairs are the game's own localization, the same entry id read in Japanese and in English,
 * which is the only "real" authority on what FromSoft's translators called a thing.
 * The event names come from the same installation 'emeld' files rather than from a third-party dump,
 * so everything downstream traces to the game and nothing else
 *
 * Usage:
 *  ./gradlew :tools:extractText -Pds1=/path/to/DATA -Pout=build/text
 */
public fun main(args: Array<String>) {
    val root = Path.of(args[0])
    val out = Path.of(args[1])

    require(Files.isDirectory(root)) { "$root is not a directory" }

    openGameFiles(installationAt(root)).use { files ->
        val languages = TextLanguage.entries.filter { files.hasText(it) }
        println("Languages present: ${languages.joinToString { it.folder }}")

        require(TextLanguage.Japanese in languages && TextLanguage.English in languages) {
            "Both Japanese and English text are needed to pair terms, and this installation has " +
                    languages.joinToString { it.folder }
        }

        Files.createDirectories(out)
        write(out.resolve(TERMS_FILE), TermCorpus(languages.map { it.folder }, files.termPairs()))
        write(out.resolve(EVENT_NAMES_FILE), EventNameCorpus(files.eventNames()))
    }
}

private fun installationAt(root: Path): Installation = Installation(
    id = InstallationId.forRoot(root),
    root = root,
    executable = root.resolve(GameEdition.PrepareToDie.executableName),
    build = GameBuild(GameEdition.PrepareToDie)
)

private fun GameFiles.termPairs(): List<TermPair> {
    val japanese = loadText(TextLanguage.Japanese, TextCategory.naming)
    val english = loadText(TextLanguage.English, TextCategory.naming)

    return TextCategory.naming.flatMap { category ->
        val ja = japanese[category]?.strings.orEmpty()
        val en = english[category]?.strings.orEmpty()
        val shared = ja.keys intersect en.keys

        println("${category.label}: ${ja.size} ja, ${en.size} en, ${shared.size} paired")
        shared.sorted().mapNotNull { id ->
            val source = ja.getValue(id)
            val target = en.getValue(id)
            if (source.isBlank() || target.isBlank()) null else TermPair(category.name, id, source, target)
        }
    }
}

private fun GameFiles.eventNames(): List<EventName> {
    val scripts = availableScripts(availableMaps(loadFileManifest(GameEdition.PrepareToDie)))

    return scripts.flatMap { script ->
        val names = loadScriptNames(script)?.all.orEmpty()
        println("${script.label}: ${names.size} event names")
        names.toSortedMap().map { (eventId, name) -> EventName(script.label, eventId, name) }
    }
}

private inline fun <reified T> write(path: Path, corpus: T) {
    Files.writeString(path, JSON.encodeToString(corpus))
    println("Wrote $path")
}