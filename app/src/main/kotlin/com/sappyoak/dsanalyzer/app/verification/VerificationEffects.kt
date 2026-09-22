package com.sappyoak.dsanalyzer.app.verification

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

import com.sappyoak.dsanalyzer.app.store.EffectRunner
import com.sappyoak.dsanalyzer.game.Installation
import com.sappyoak.dsanalyzer.game.InstallationFingerprint
import com.sappyoak.dsanalyzer.game.InstallationId
import com.sappyoak.dsanalyzer.game.files.InstallationFiles
import com.sappyoak.dsanalyzer.game.files.InstalledFileIndex
import com.sappyoak.dsanalyzer.game.fingerprint
import com.sappyoak.dsanalyzer.game.verification.loadFileManifest
import com.sappyoak.dsanalyzer.game.verification.verify


public class VerificationEffects(
    private val cache: VerificationCache,
    private val files: InstallationFiles,
    private val index: InstalledFileIndex
) : EffectRunner<VerificationEffect, VerificationMessage> {
    /** One verification per installation at a time */
    override fun keyOf(effect: VerificationEffect): Any? = when (effect) {
        is VerificationEffect.Run -> effect.installation.id
        else -> null
    }

    public override fun execute(effect: VerificationEffect): Flow<VerificationMessage> =
        flow { perform(effect)?.let { emit(it) } }

    public override fun onFailure(effect: VerificationEffect, failure: Throwable): VerificationMessage? {
        val reason = failure.message ?: failure.toString()
        return when (effect) {
            is VerificationEffect.Fingerprint -> VerificationMessage.Failed(effect.installation.id, reason)
            is VerificationEffect.Run -> VerificationMessage.Failed(effect.installation.id, reason)
            else -> null
        }
    }

    private suspend fun perform(effect: VerificationEffect): VerificationMessage? = when (effect) {
        VerificationEffect.LoadCache -> VerificationMessage.CacheLoaded(cache.read())

        is VerificationEffect.SaveCache -> {
            cache.write(effect.records)
            null
        }

        is VerificationEffect.Fingerprint -> VerificationMessage.Fingerprinted(
            installation = effect.installation,
            fingerprint = withContext(Dispatchers.IO) {
                effect.installation.fingerprint()
            }
        )

        is VerificationEffect.Run -> {
            // Runs only when the fingerprint changes, so any open copy of the files is stale
            files.retire(effect.installation.id)
            VerificationMessage.Completed(
                installationId = effect.installation.id,
                fingerprint = effect.fingerprint,
                result = verify(
                    manifest = withContext(Dispatchers.IO) {
                        loadFileManifest(effect.installation.build.edition)
                    },
                    listing = index.list(effect.installation)
                )
            )
        }
    }
}

public sealed interface VerificationEffect {
    public data object LoadCache : VerificationEffect

    public data class SaveCache(
        public val records: Map<InstallationId, VerificationRecord>
    ) : VerificationEffect

    public data class Fingerprint(public val installation: Installation) : VerificationEffect

    public data class Run(
        public val installation: Installation,
        public val fingerprint: InstallationFingerprint
    ) : VerificationEffect
}