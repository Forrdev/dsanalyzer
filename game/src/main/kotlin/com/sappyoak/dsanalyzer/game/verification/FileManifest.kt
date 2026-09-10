package com.sappyoak.dsanalyzer.game.verification

import com.sappyoak.dsanalyzer.game.GameEdition

/** Every path a pristine installation of an edition should contain */
public data class FileManifest(
    public val edition: GameEdition,
    public val paths: Set<String>
)

