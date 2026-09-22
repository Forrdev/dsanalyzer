package com.sappyoak.dsanalyzer.game.verification

import com.sappyoak.dsanalyzer.formats.archive.archivePathHash
import com.sappyoak.dsanalyzer.formats.archive.normalizeArchivePath
import com.sappyoak.dsanalyzer.game.files.FileListing

/**
 * Compares what an installation contains against what it should container
 */
fun verify(manifest: FileManifest, listing: FileListing): VerificationResult = when (listing) {
    is FileListing.Hashed -> {
        val expected = manifest.paths.associateBy(::archivePathHash)
        VerificationResult(
            missing = expected.filterKeys { it !in listing.hashes }.values.sorted(),
            unidentified = (listing.hashes - expected.keys)
                .map { "0x${it.toString(16).padStart(8, '0')}" }
                .sorted()
        )
    }

    is FileListing.Named -> {
        val expected = manifest.paths.mapTo(mutableSetOf(), ::normalizeArchivePath)
        val actual = listing.paths.mapTo(mutableSetOf(), ::normalizeArchivePath)

        VerificationResult(
            missing = (expected - actual).sorted(),
            unidentified = (actual - expected).sorted()
        )
    }
}

/** Outcome of checking an installation against a manifest */
public data class VerificationResult(
    public val missing: List<String>,
    public val unidentified: List<String>
)
