package com.sappyoak.dsanalyzer.game.assets.fs

import java.io.BufferedOutputStream
import java.nio.file.Path
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import kotlin.io.path.createDirectories

import com.sappyoak.dsanalyzer.game.assets.GameAsset
import com.sappyoak.dsanalyzer.game.assets.GameAssetResolver

class ExtractionFileSink(private val root: Path) {
    private val assets = GameAssetResolver(root)

    fun write(path: String, bytes: ByteArray): Result<Unit> = runCatching {
        val target = assets.target(GameAsset(path))
        target.parent?.createDirectories()
        BufferedOutputStream(Files.newOutputStream(
            target,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
            StandardOpenOption.WRITE
        )).use { stream -> stream.write(bytes) }
    }
}