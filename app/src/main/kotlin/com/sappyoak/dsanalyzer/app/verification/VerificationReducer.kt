package com.sappyoak.dsanalyzer.app.verification

import com.sappyoak.dsanalyzer.app.store.Transition
import com.sappyoak.dsanalyzer.app.store.with

public fun reduceVerification(
    state: VerificationState,
    message: VerificationMessage
) : Transition<VerificationState, VerificationEffect> = when (message) {
    VerificationMessage.Start -> state.with(VerificationEffect.LoadCache)

    is VerificationMessage.CacheLoaded -> state.copy(
        statuses = message.records.mapValues { (_, record) -> VerificationStatus.Complete(record) }
    ).with()

    is VerificationMessage.VerificationRequested -> state.copy(viewing = message.installation.id).with()

    VerificationMessage.Dismissed -> state.copy(viewing = null).with()
}