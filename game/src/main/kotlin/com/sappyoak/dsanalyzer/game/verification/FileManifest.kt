package com.sappyoak.dsanalyzer.game.verification

import com.sappyoak.dsanalyzer.game.GameEdition

/** Every path a pristine installation of an edition should contain */
public data class FileManifest(
    public val edition: GameEdition,
    public val paths: Set<String>
)

/** Reads a manifest from plain text, one path per line */
fun GameEdition.parseFileManifest(lines: Sequence<String>): FileManifest = FileManifest(
    edition = this,
    paths = lines
        .map { it.trim() }
        .filter { it.isNotEmpty() && !it.startsWith("#") }
        .toSet()
)

