package com.sappyoak.dsanalyzer.app.manifest

import com.sappyoak.dsanalyzer.game.GameEdition
import com.sappyoak.dsanalyzer.game.verification.FileManifest

/** Supplies the list of files a pristine installation of an edition should contain */
public interface ManifestSource {
    public suspend fun load(edition: GameEdition): FileManifest
}