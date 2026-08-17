package com.sappyoak.dsanalyzer.game.assets.fs

import java.nio.file.Path


import com.sappyoak.dsanalyzer.game.assets.GameAsset
import com.sappyoak.dsanalyzer.game.assets.GameAssetResolver
import com.sappyoak.dsanalyzer.shared.io.writeBytes

class ExtractionFileSink(private val root: Path) {
    private val assets = GameAssetResolver(root)

    fun write(path: String, bytes: ByteArray): Result<Unit> {
        val target = assets.target(GameAsset(path))
        return target.writeBytes(bytes)
    }
}