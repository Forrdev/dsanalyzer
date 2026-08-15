package com.sappyoak.dsanalyzer.domain

import java.nio.file.Path
import kotlin.io.path.exists

import com.sappyoak.dsanalyzer.shared.readBytes

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

/**
 * Asset names, not file system paths.
 *
 * These identify files inside the game and are the same whether it is packed ,extracted or
 * unpacked in place. A packed lookup hashes the name with no filesystem involved at all.
 * Turing one into a real location is [GameAssetResolver]'s job
 */
object GameAssetPaths {
    const val DCX_EXTENSION = ".dcx"
    const val BHD5_EXTENSION = ".bhd5"
    const val BDT_EXTENSION = ".bdt"

    val ARCHIVE_NAMES = listOf("dvdbnd0", "dvdbnd1", "dvdbnd2", "dvdbnd3")
    val LOOSE_FILE_MARKERS = listOf(
        "event/common.emevd",
        "map/MapStudio/m10_02_00_00.msb",
        "chr/c0000.anibnd"
    )

    val REMASTERED_ONLY_FILE_MARKERS = listOf(
        "map/m18_01_00_00",
        "sfx/frpg_sfxbnd_m18.ffxbnd.dcx"
    )

    val CommonEMEVD = GameAsset("/event/common.emevd$DCX_EXTENSION")
    val GameParam = GameAsset("/param/gameparam/gameparam.parambnd$DCX_EXTENSION")
    val EventCommon = GameAsset("/script/eventcommon.luabnd$DCX_EXTENSION")
    val AICommon = GameAsset("/script/aicommon.luabnd$DCX_EXTENSION")

    fun archiveFilePair(name: String): Pair<String, String> =
        archiveHeader(name) to archiveData(name)

    fun archiveHeader(name: String) = "$name.$BHD5_EXTENSION"
    fun archiveData(name: String) = "$name.$BDT_EXTENSION"

    fun msb(mapId: String) = GameAsset("/map/mapstudio/$mapId.msb")
    fun navmesh(mapId: String) = GameAsset("/map/$mapId/$mapId.nvmbnd$DCX_EXTENSION")

    fun lowCollisionHeader(mapId: String) = collisionHeader(mapId, true)
    fun lowCollisionData(mapId: String) = collisionData(mapId, true)
    fun lowCollisionPair(mapId: String): Pair<GameAsset, GameAsset> =
        lowCollisionHeader(mapId) to lowCollisionData(mapId)

    fun highCollisionHeader(mapId: String) = collisionHeader(mapId, false)
    fun highCollisionData(mapId: String) = collisionData(mapId, false)
    fun highCollisionPair(mapId: String): Pair<GameAsset, GameAsset> =
        highCollisionHeader(mapId) to highCollisionData(mapId)

    fun emevd(mapId: String) = GameAsset("/event/$mapId.emevd$DCX_EXTENSION")
    fun emeld(mapId: String) = GameAsset("/event/$mapId.emeld$DCX_EXTENSION")
    fun aiScripts(mapId: String) = GameAsset("/script/$mapId.luabnd$DCX_EXTENSION")

    fun characterAnibnd(chrId: String) = GameAsset("/chr/$chrId.anibnd$DCX_EXTENSION")
    fun characterChrbnd(chrId: String) = GameAsset("/chr/$chrId.chrbnd$DCX_EXTENSION")
    fun playerCategoryAnibnd(decade: Int) = GameAsset("/chr/c0000_at${decade}x.anibnd$DCX_EXTENSION")
    fun playerPairedAnibnd(enemyChrId: String) = GameAsset("/chr/c0000_$enemyChrId.anibnd$DCX_EXTENSION")

    fun getArchivePairs(): List<Pair<String, String>> = ARCHIVE_NAMES.map(::archiveFilePair)

    private fun collisionHeader(mapId: String, low: Boolean): GameAsset {
        val prefix = if (low) "l" else "h"
        return GameAsset("/map/$mapId/$prefix${mapId.removePrefix("m")}.hkxbhd$DCX_EXTENSION")
    }

    private fun collisionData(mapId: String, low: Boolean): GameAsset {
        val prefix = if (low) "l" else "h"
        return GameAsset("/map/$mapId/$prefix${mapId.removePrefix("m")}.hkxbdt")
    }
}