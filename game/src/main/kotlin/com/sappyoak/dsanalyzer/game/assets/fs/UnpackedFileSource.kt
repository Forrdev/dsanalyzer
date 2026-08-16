package com.sappyoak.dsanalyzer.game.assets.fs

import java.nio.file.Path

import com.sappyoak.dsanalyzer.game.assets.*

class UnpackedFileSource(private val root: Path) {
    private val assets = GameAssetResolver(root)

    fun read(path: String): ByteArray? = assets.read(path)
    fun exists(path: String): Boolean = assets.exists(path)
}