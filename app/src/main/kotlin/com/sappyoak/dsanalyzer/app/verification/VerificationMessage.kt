package com.sappyoak.dsanalyzer.app.verification

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationFingerprint
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.verification.VerificationResult

public sealed interface VerificationMessage {
    public data object Start : VerificationMessage

    public data class VerificationRequested(public val installation: Installation) : VerificationMessage

    public data class CacheLoaded(
        public val records: Map<InstallationId, VerificationRecord>
    ) : VerificationMessage

    public data object Dismissed : VerificationMessage

    public data class Fingerprinted(
        public val installation: Installation,
        public val fingerprint: InstallationFingerprint
    ) : VerificationMessage

    public data class Completed(
        public val installationId: InstallationId,
        public val fingerprint: InstallationFingerprint,
        public val result: VerificationResult
    ) : VerificationMessage

    public data class Failed(
        public val installationId: InstallationId,
        public val reason: String
    ) : VerificationMessage
}
