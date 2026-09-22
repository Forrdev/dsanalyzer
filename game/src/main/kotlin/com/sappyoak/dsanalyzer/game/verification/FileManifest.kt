package com.sappyoak.dsanalyzer.game.verification

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.PREPARE_TO_DIE_ARCHIVE_STEMS
import com.sappyoak.dsanalyzer.game.files.GamePath

private const val RESOURCE_ROOT = "/manifests"
private const val DSR_MANIFEST_FILE_NAME = "dsr_file_list.txt"

/** Every path a pristine installation of an edition should contain */
public data class FileManifest(
    public val edition: GameEdition,
    public val paths: Set<GamePath>
)

public fun loadFileManifest(edition: GameEdition): FileManifest = FileManifest(
    edition = edition,
    paths = manifestResourceNames(edition).flatMapTo(mutableSetOf(), ::readEntries)
)

private fun manifestResourceNames(edition: GameEdition): List<String> = when (edition) {
    GameEdition.PrepareToDie -> PREPARE_TO_DIE_ARCHIVE_STEMS.map { "$it.txt" }
    GameEdition.Remastered -> listOf(DSR_MANIFEST_FILE_NAME)
}

private fun readEntries(name: String): Set<GamePath> {
    val resource = "$RESOURCE_ROOT/$name"
    val stream = checkNotNull(FileManifest::class.java.getResourceAsStream(resource)) {
        "Bundled manifest $resource is missing"
    }

    return stream.bufferedReader().use { parseManifestEntries(it.lineSequence()) }
}

private fun parseManifestEntries(lines: Sequence<String>): Set<GamePath> = lines
    .map { it.trim() }
    .filter { it.isNotEmpty() && !it.startsWith("#") }
    .mapTo(mutableSetOf(), GamePath::of)