package com.sappyoak.dsanalyzer.game.verification

import java.util.concurrent.ConcurrentHashMap

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.PREPARE_TO_DIE_ARCHIVE_STEMS
import com.sappyoak.dsanalyzer.game.bundled
import com.sappyoak.dsanalyzer.game.files.GamePath

private const val RESOURCE_ROOT = "/manifests"
private const val DSR_MANIFEST_FILE_NAME = "dsr_file_list.txt"

/** Every path a pristine installation of an edition should contain */
public data class FileManifest(
    public val edition: GameEdition,
    public val paths: Set<GamePath>
)

private val manifests = ConcurrentHashMap<GameEdition, FileManifest>()

public fun loadFileManifest(edition: GameEdition): FileManifest =
    manifests.computeIfAbsent(edition, ::readManifests)

private fun readManifests(edition: GameEdition): FileManifest = FileManifest(
    edition = edition,
    paths = manifestResourceNames(edition).flatMapTo(mutableSetOf(), ::readEntries)
)

private fun manifestResourceNames(edition: GameEdition): List<String> = when (edition) {
    GameEdition.PrepareToDie -> PREPARE_TO_DIE_ARCHIVE_STEMS.map { "$it.txt" }
    GameEdition.Remastered -> listOf(DSR_MANIFEST_FILE_NAME)
}

private fun readEntries(name: String): Set<GamePath> =
    bundled("$RESOURCE_ROOT/$name") { parseManifestEntries(it.lineSequence()) }

private fun parseManifestEntries(lines: Sequence<String>): Set<GamePath> = lines
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") }
    .mapTo(mutableSetOf(), GamePath::of)