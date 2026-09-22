package com.sappyoak.dsanalyzer.app.verification

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.verification.VerificationResult

public fun reduceVerification(
    state: VerificationState,
    message: VerificationMessage
) : Transition<VerificationState, VerificationEffect> = when (message) {
    VerificationMessage.Start -> state.with(VerificationEffect.LoadCache)

    is VerificationMessage.CacheLoaded -> state.copy(
        statuses = message.records.mapValues { (_, record) -> VerificationStatus.Complete(record) }
    ).with()

    is VerificationMessage.VerificationRequested -> state
        .copy(viewing = message.installation.id)
        .with(VerificationEffect.Fingerprint(message.installation))

    is VerificationMessage.Fingerprinted -> {
        val cached = state.statuses[message.installation.id] as? VerificationStatus.Complete

        if (cached?.record?.fingerprint == message.fingerprint) {
            // unchanged
            state.with()
        } else {
            state.setting(message.installation.id, VerificationStatus.Running)
                .with(VerificationEffect.Run(message.installation, message.fingerprint))
        }
    }

    is VerificationMessage.Completed -> {
        val record = VerificationRecord(
            fingerprint = message.fingerprint,
            missing = message.result.missing,
            unidentified = message.result.unidentified
        )

        val next = state.setting(message.installationId, VerificationStatus.Complete(record))
        next.with(VerificationEffect.SaveCache(next.records()))
    }

    is VerificationMessage.Failed -> state
        .setting(message.installationId, VerificationStatus.Failed(message.reason))
        .with()

    VerificationMessage.Dismissed -> state.copy(viewing = null).with()
}

private fun VerificationState.setting(
    id: InstallationId,
    status: VerificationStatus
): VerificationState = copy(statuses + (id to status))

private fun VerificationState.records(): Map<InstallationId, VerificationRecord> =
    statuses.mapNotNull { (id, status) ->
        (status as? VerificationStatus.Complete)?.let { id to it.record }
    }.toMap()