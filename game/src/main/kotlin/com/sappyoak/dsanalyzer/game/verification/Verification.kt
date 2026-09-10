package com.sappyoak.dsanalyzer.game.verification

import com.sappyoak.dsanalyzer.game.files.FileListing

/**
 * Compares what an installation contains against what it should container
 */
fun verify(manifest: FileManifest, listing: FileListing): VerificationResult = when (listing) {
    FileListing.Unsupported -> VerificationResult.ListingUnsupported

    is FileListing.Available -> VerificationResult.Checked(
        missing = (manifest.paths - listing.paths).sorted(),
        unidentified = (listing.paths - manifest.paths).sorted()
    )
}

/** Outcome of checking an installation against a manifest */
public sealed interface VerificationResult {
    public data class Checked(
        public val missing: List<String>,
        public val unidentified: List<String>
    ) : VerificationResult {
        public val hasAllFiles: Boolean get() = missing.isEmpty()
    }

    /** The installation could not be enumerated, so nothing was compared */
    public data object ListingUnsupported : VerificationResult
}