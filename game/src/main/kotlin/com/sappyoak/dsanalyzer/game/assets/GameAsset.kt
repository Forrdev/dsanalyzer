package com.sappyoak.dsanalyzer.game.assets

/**
 * The name of a file inside the game, as distinct from where it sits
 *
 * Archived assets are DCX-compressed and named within the suffix; extracting decompresses them
 * and drops it. So one asset has two physical spellings, and which applies depends on the source
 * rather than the asset
 */
@JvmInline
value class GameAsset(val name: String) {
    val archived: String get() = name
    val extracted: String get() = name.removePrefix("/").removeSuffix(GameAssetPaths.DCX_EXTENSION)
    val fileName: String get() = name.substringAfter('/')
    val isCompressed: Boolean get() = name.endsWith(GameAssetPaths.DCX_EXTENSION, ignoreCase = true)
    val directory: String get() = name.substringBeforeLast('/', "").removePrefix("/")

    override fun toString() = name
}