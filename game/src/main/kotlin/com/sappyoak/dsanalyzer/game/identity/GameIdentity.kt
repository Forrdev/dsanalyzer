package com.sappyoak.dsanalyzer.game.identity

import kotlinx.serialization.Serializable

@Serializable
data class GameIdentity(
    val version: GameVersion,
    val buildId: GameBuildId,
    val buildLabel: GameBuildLabel? = null,
    val runtimeVersion: String? = null
) {

    val pathSegment: String get() = "${version.shortName.lowercase()}-$buildId"
    val displayName: String get() = buildString {
        append(version.displayName)
        runtimeVersion?.let { append(" -$it") }
        buildLabel?.let { append (" ($it)") }
    }

    fun sameVersionAs(other: GameIdentity): Boolean = version == other.version
    fun sameVersionAsStrict(other: GameIdentity): Boolean = sameVersionAs(other) && runtimeVersion == other.runtimeVersion

    fun withBuildLabel(label: GameBuildLabel): GameIdentity = copy(buildLabel = label)

    companion object {
        operator fun invoke(
            version: GameVersion,
            buildId: String,
            buildLabel: String? = null,
            runtimeVersion: String? = null
        ): GameIdentity = GameIdentity(
            version = version,
            buildId = GameBuildId.of(buildId),
            buildLabel = buildLabel?.let { GameBuildLabel.of(it) },
            runtimeVersion = runtimeVersion
        )

        fun unknown(version: GameVersion): GameIdentity =
            GameIdentity(version, GameBuildId.Unknown)
    }
}

fun GameIdentity.withBuildLabel(label: String): GameIdentity =
    withBuildLabel(GameBuildLabel.of(label))

