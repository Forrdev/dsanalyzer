package com.sappyoak.dsanalyzer.game

import kotlinx.serialization.Serializable

/**
 * Version of the game executable. Reading it means parsing PE resource data, which will be a responsibility
 * of the native layer at a later date. Until then every build resolves to [Unknown] and only the edition is known
 */
@Serializable
public sealed interface BuildVersion {
    @Serializable
    data object Unknown : BuildVersion

    @Serializable
    data class Known(
        public val major: Int,
        public val minor: Int,
        public val build: Int,
        public val revision: Int
    ) : BuildVersion
}

@Serializable
public data class GameBuild(
    public val edition: GameEdition,
    public val version: BuildVersion = BuildVersion.Unknown
)