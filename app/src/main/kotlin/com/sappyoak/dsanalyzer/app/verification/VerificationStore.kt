package com.sappyoak.dsanalyzer.app.verification

import kotlinx.coroutines.CoroutineScope

import com.sappyoak.dsanalyzer.app.store.Store

public class VerificationStore(
    scope: CoroutineScope,
    effects: VerificationEffects
) : Store<VerificationState, VerificationMessage, VerificationEffect>(
    scope = scope,
    name = "verification",
    initial = VerificationState(),
    reduce = ::reduceVerification,
    effects = effects
)