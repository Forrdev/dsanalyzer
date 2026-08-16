package com.sappyoak.dsanalyzer.game.assets

import java.nio.file.Path
import kotlin.io.path.exists

import com.sappyoak.dsanalyzer.shared.readBytes

/**
 * Resolves assets against one root
 *
 * Separate  from the asset names rather than folded into them. Identity stays a property
 * of the asset and resolution belongs to whoever has a directory. A resolver is that,
 * and [withRoot] is how one source's view becomes anomer's.
 */
class GameAssetResolver(val root: Path) {
    fun resolve(asset: GameAsset): Path? {
        val extracted = root.resolve(asset.extracted)
        if (extracted.exists()) return extracted

        val compressed = root.resolve(asset.archived.removePrefix("/"))
        return if (compressed.exists()) compressed else null
    }

    fun target(asset: GameAsset): Path = root.resolve(asset.extracted)
    fun exists(asset: GameAsset): Boolean = resolve(asset) != null

    fun read(asset: GameAsset): ByteArray? =
        resolve(asset)?.readBytes()?.getOrNull()

    fun withRoot(other: Path): GameAssetResolver = GameAssetResolver(other)

    inline fun <T> against(other: Path, block: (GameAssetResolver) -> T): T = block(withRoot(other))

    override fun toString() = "assets at $root"
}

fun GameAssetResolver.resolve(path: String): Path? = resolve(GameAsset(path))
fun GameAssetResolver.read(path: String): ByteArray? = read(GameAsset(path))
fun GameAssetResolver.exists(path: String): Boolean = exists(GameAsset(path))
fun GameAssetResolver.target(path: String): Path = target(GameAsset(path))