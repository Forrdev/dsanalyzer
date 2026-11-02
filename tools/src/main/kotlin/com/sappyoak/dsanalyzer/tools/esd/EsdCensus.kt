package com.sappyoak.dsanalyzer.tools.esd

import java.nio.file.Path

import com.sappyoak.dsanalyzer.formats.esd.expr.Decoded
import com.sappyoak.dsanalyzer.formats.esd.expr.decode
import com.sappyoak.dsanalyzer.formats.esd.programs
import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.files.GameFiles
import com.sappyoak.dsanalyzer.game.files.openGameFiles
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.world.esd.EsdSource
import com.sappyoak.dsanalyzer.game.world.esd.availableTalk
import com.sappyoak.dsanalyzer.game.world.esd.loadEsd
import com.sappyoak.dsanalyzer.game.world.maps.availableMaps
import com.sappyoak.dsanalyzer.tools.installationAt

/** Failing programs printed under each reason, so a gap can be read without the whole list */
private const val SHOWN_EXAMPLES = 3

public fun main(args: Array<String>) {
    require(args.size == 2) { "Expected <DATA directory> <ptde|dsr>" }
    val edition = when (args[1]) {
        "ptde" -> GameEdition.PrepareToDie
        "dsr" -> GameEdition.Remastered
        else -> error("Edition must be ptde or dsr, not '${args[1]}'")
    }

    openGameFiles(installationAt(Path.of(args[0]), edition)).use { files ->
        val sources = listOf(EsdSource.Player) +
                files.availableTalk(files.availableMaps(loadFileManifest(edition)))
                    .flatMap { (map, ids) -> ids.map { EsdSource.Talk(map, it) } }
        print(census(files, sources))
    }
}

private fun census(files: GameFiles, sources: List<EsdSource>): String = buildString {
    var decoded = 0
    val unreadable = mutableListOf<String>()
    val undecodable = mutableMapOf<String, MutableList<String>>()

    for (source in sources) {
        val esd = runCatching { files.loadEsd(source) }.getOrElse { failure ->
            unreadable += "$source: ${failure.message}"
            null
        } ?: continue

        esd.programs().forEach { program ->
            when (val result = program.decode()) {
                is Decoded.Expr -> decoded++
                is Decoded.Undecodable ->
                    undecodable.getOrPut(result.reason) { mutableListOf() } += "$source @${result.at}: ${program.hex()}"
            }
        }
    }

    appendLine("${sources.size} ESDs, ${unreadable.size} unreadable")
    unreadable.forEach { appendLine("  $it") }
    appendLine("$decoded programs decoded, ${undecodable.values.sumOf { it.size }} undecodable")
    undecodable.entries.sortedByDescending { it.value.size }.forEach { (reason, examples) ->
        appendLine("  ${examples.size} × $reason")
        examples.take(SHOWN_EXAMPLES).forEach { appendLine("      $it") }
    }
}
