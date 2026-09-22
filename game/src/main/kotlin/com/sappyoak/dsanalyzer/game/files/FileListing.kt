package com.sappyoak.dsanalyzer.game.files

public sealed interface FileListing {
    public data class Hashed(public val hashes: Set<UInt>) : FileListing
    public data class Named(public val paths: Set<GamePath>) : FileListing
}
