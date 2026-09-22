package com.sappyoak.dsanalyzer.game.verification

import com.sappyoak.dsanalyzer.game.files.FileListing
import com.sappyoak.dsanalyzer.game.files.GamePath

/**
 * Compares what an installation contains against what it should container
 */
fun verify(manifest: FileManifest, listing: FileListing): VerificationResult = when (listing) {
    is FileListing.Hashed -> {
        val expected = manifest.paths.associateBy { it.hash }
        VerificationResult(
            missing = expected.filterKeys { it !in listing.hashes }.values.map { it.value }.sorted(),
            unidentified = (listing.hashes - expected.keys)
                .map { "0x${it.toString(16).padStart(8, '0')}" }
                .sorted()
        )
    }

    is FileListing.Named -> VerificationResult(
        missing = (manifest.paths - listing.paths).map { it.value }.sorted(),
        unidentified = (listing.paths - manifest.paths).map { it.value }.sorted()
    )
}

/** Outcome of checking an installation against a manifest */
public data class VerificationResult(
    public val missing: List<String>,
    public val unidentified: List<String>
)
