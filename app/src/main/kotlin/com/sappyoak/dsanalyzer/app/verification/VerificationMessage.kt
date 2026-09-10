package com.sappyoak.dsanalyzer.app.verification

import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationId

public sealed interface VerificationMessage {
    public data object Start : VerificationMessage

    public data class VerificationRequested(public val installation: Installation) : VerificationMessage

    public data class CacheLoaded(
        public val records: Map<InstallationId, VerificationRecord>
    ) : VerificationMessage

    public data object Dismissed : VerificationMessage
}
