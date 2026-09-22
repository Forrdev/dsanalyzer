package com.sappyoak.dsanalyzer.game.files

import com.sappyoak.dsanalyzer.formats.archive.archivePathHash
import com.sappyoak.dsanalyzer.formats.archive.normalizeArchivePath

/** The name the game knows a file by, such as '/map/mapstudio/m10_02_00_00.msb' */
@JvmInline
public value class GamePath private constructor(public val value: String) {
    public val hash: UInt get() = archivePathHash(value)

    override fun toString(): String = value

    public companion object {
        public fun of(path: String): GamePath = GamePath(normalizeArchivePath(path))
    }
}