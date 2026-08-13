package com.sappyoak.dsanalyzer.domain

import kotlinx.serialization.Serializable

/**
 * Represents which game, and which build of it
 *
 * [version] is the *kind*, either PTDE or Remastered.
 * [buildId] is the *content*, which patch, or which modded state
 *
 * A version number would read better, and we would have to parse it out of a PE
 * resource or a file the game does not obviously provide (afaik) because we need this
 * value *before* any game runtime has been read, and in cases where one will never be read.
 * The question the identity actually answers is "did the files change since we last inspected",
 * and a content digest answers that directly without needing to knowing anything about a versioning
 * scheme directly.
 *
 * It also handles the case a version number would not: a modded installation is a different build
 * event though it reports the same version, and analyzing it against a cache based on stock
 * files would silently describe content the game is not running
 */

@Serializable
data class GameIdentity(
    val version: GameVersion,
    val buildId: String,
    /** Human-readable label where one could be determined, otherwise null */
    val buildLabel: String? = null
) {
    /** Directory-safe, and readable enough to identify by eye */
    val pathSegment: String get() = "${version.shortName.lowercase()}-$buildId"

    val displayName: String get() = buildString {
        append(version.displayName)
        buildLabel?.let { append(" ($it)")}
    }

    fun sameKind(other: GameIdentity): Boolean = version == other.version

    companion object {
        private const val FNV_OFFSET = -3750763034362895579L
        private const val FNV_PRIME = 1099511628211L
        /** Bytes sampled per file. Enough to differ on any real change, cheap enough to be free */
        private const val SAMPLE_POINTS = 4096

        /**
         * Builds an identity from bytes that change when the installation does
         */
        fun from(
            version: GameVersion,
            samples: List<ByteArray>,
            buildLabel: String? = null
        ) : GameIdentity {
            var hash = FNV_OFFSET

            for (sample in samples) {
                // Sampled rather than hashed whole. An archive header is hundreds of kilobytes
                // and hashing all of it per launch would be a noticeable pause for a value whose
                // only job is to differ when the file differs.
                val step = maxOf(1, sample.size / SAMPLE_POINTS)
                var index = 0
                while (index < sample.size) {
                    hash = (hash xor sample[index].toLong()) * FNV_PRIME
                    index += step
                }

                // Length included, so two files sampled to the same bytes but of different
                // sizes do not collide
                hash = (hash xor sample.size.toLong()) * FNV_PRIME
            }

            return GameIdentity(
                version = version,
                buildId = (hash and 0xFFFFFFFFL).toString(16).padStart(8, '0'),
                buildLabel = buildLabel
            )
        }

        fun unknown(version: GameVersion): GameIdentity =
            GameIdentity(version, buildId = "unknown")
    }
}