package com.sappyoak.dsanalyzer.app.verification

import kotlinx.serialization.Serializable

import com.sappyoak.dsanalyzer.game.InstallationFingerprint
import com.sappyoak.dsanalyzer.game.InstallationId

public data class VerificationState(
    public val statuses: Map<InstallationId, VerificationStatus> = emptyMap(),
    public val viewing: InstallationId? = null
)

/**
 * A cached verdict. The fingerprint it was taken against is stored with it, so
 * a changed installation is a re-check instead of showing a stale one
 */
@Serializable
public data class VerificationRecord(
    public val fingerprint: InstallationFingerprint,
    public val missing: List<String>,
    public val unidentified: List<String>
)

public sealed interface VerificationStatus {
    public data object Running : VerificationStatus

    public data class Complete(public val record: VerificationRecord) : VerificationStatus

    public data class Failed(public val reason: String) : VerificationStatus
}