package com.sappyoak.dsanalyzer.game

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.nio.file.Path
import java.security.MessageDigest

/**
 * Identity of an installation, derived from its canonical root path rather than assigned.
 * Adding the game folder twice therefore produces the same id and needs no de-duplication
 */
@JvmInline
@Serializable
public value class InstallationId(public val value: String) {
    override fun toString() = value

    public companion object {
        private const val LENGTH = 16

        public fun forRoot(root: Path): InstallationId {
            val canonical = root.toRealPath().toString()
            val digest = MessageDigest.getInstance("SHA-256").digest(canonical.toByteArray())
            return InstallationId(digest.joinToString("") { "%02x".format(it) }.take(LENGTH) )
        }
    }
}


@Serializable
public data class Installation(
    public val id: InstallationId,
    @Contextual public val root: Path,
    @Contextual public val executable: Path,
    public val build: GameBuild

)